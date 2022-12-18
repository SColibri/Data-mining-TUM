import threading
import time
import logging
import pytz
import pandas as pd
import numpy as np

from embeddingStructure import embeddingStructure
from batch import batch
from tqdm import tqdm
import matplotlib.pyplot as plt

import utils as cSim

format = "%(asctime)s: %(message)s"
logging.basicConfig(format=format, level=logging.INFO, datefmt="%H:%M:%S")
logging.info("Main    : Starting")

filename = "/home/ubuntu/sampled_embeddings.csv"

batch_01 = batch()
batch_02 = batch()
batch_03 = batch()
batch_04 = batch()

print("---- batch 04 -----")
a = threading.Thread(target=batch_04.load, args=(filename, 0, 1E4)) #100,000
a.start()

print("---- batch 03 -----")
b = threading.Thread(target=batch_03.load, args=(filename, 0, 1E3)) #10,000
b.start()

print("---- batch 02 -----")
y = threading.Thread(target=batch_02.load, args=(filename, 0, 1E2)) #1,000
y.start()

print("---- batch 01 -----")
x = threading.Thread(target=batch_01.load, args=(filename, 0, 1E1)) #100 values
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
for batch in tqdm(batches):
    sum = 0
    testtime = time.time()
    for i in range(1, len(batch.items) - 1):
        sum += cSim.cosineSimilarity(batch.items[i-1].data,batch.items[i].data)
    finaltime = time.time()
    print(len(batch.items), '->',finaltime-testtime,'SUM',sum)

a = np.random.random((16, 16))
plt.imshow(a, cmap='hot', interpolation='nearest')
plt.show()