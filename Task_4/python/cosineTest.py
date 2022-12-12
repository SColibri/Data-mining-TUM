import threading
import time
import logging
import pytz
import pandas as pd

from embeddingStructure import embeddingStructure
from batch import batch

import utils as group2

format = "%(asctime)s: %(message)s"
logging.basicConfig(format=format, level=logging.INFO, datefmt="%H:%M:%S")
logging.info("Main    : Starting")

filename = "/home/ubuntu/sampled_embeddings.csv"

column_names = [
    'data'
]

# https://towardsdatascience.com/machine-learning-at-scale-with-apache-spark-mllib-python-example-b32a9c74c610
# Error in database does not allow using this function
# train_df = pd.read_csv(filename, names=column_names)

batch_01 = batch()
batch_02 = batch()
batch_03 = batch()
batch_04 = batch()

print("---- batch 04 -----")
a = threading.Thread(target=batch_04.load, args=(filename, 1E4, 1.1E4)) #1,000
a.start()

print("---- batch 03 -----")
b = threading.Thread(target=batch_03.load, args=(filename, 1E5, 2E5)) #100,000
b.start()

print("---- batch 02 -----")
y = threading.Thread(target=batch_02.load, args=(filename, 1E5, 1.1E5)) #10,000
y.start()

print("---- batch 01 -----")
x = threading.Thread(target=batch_01.load, args=(filename, 1E4, 1.01E4)) #100 values
x.start()

x.join()
y.join()
a.join()
b.join()

logging.info("Main    : all done")
print( len(batch_01.items))
print( len(batch_02.items))
print( len(batch_03.items))
print( len(batch_04.items))

batches = [batch_01,batch_02,batch_03,batch_04]
for batch in batches:
    sum = 0
    testtime = time.time()
    for i in range(1, len(batch.items) - 1):
        sum += group2.cosineSimilarity(batch.items[i-1].data,batch.items[i].data)
    finaltime = time.time()
    print(len(batch.items), '->',finaltime-testtime,'SUM',sum)