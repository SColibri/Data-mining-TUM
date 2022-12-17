import threading
import time
import logging
import pytz
import pandas as pd
import numpy as np
import time

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
# batch_02 = batch()

# print("---- batch 02 -----")
# y = threading.Thread(target=batch_02.load, args=(filename, 1E5, 1.1E5))
# y.start()

print("---- batch 01 -----")
x = threading.Thread(target=batch_01.load, args=(filename, 0, 1E6)) #100 values
x.start()

x.join()
# y.join()

logging.info("Main    : all done")
print( np.random.choice( batch_01.items).data ) 
print( group2.cosineSimilarity([1,1,0],[1,0,0]) )

# contains all embedding Vectors for the batch
vectorList = batch_01.items
print("item length: ", len(vectorList[0].data))
for idx, vectorObj in enumerate(vectorList):
    if( len( vectorObj.data ) != 1024 ):
        print("YEEEET: ", vectorList.pop(idx))

# remove metoid from group to avoid checking for it in the for loop
metoid = vectorList.pop( np.random.randint( len(vectorList) - 1) ).data
metoid = np.array(metoid)
print("metoid: ", metoid )
print("shape: ", metoid.shape)



def metoidCos( givenVectorObject):
    return group2.cosineSimilarity( np.array(givenVectorObject.data), vectorB = metoid )

start = time.time()
listOfCosSimilarities = list( map(metoidCos, vectorList) )
sumAllCosSimilarities = sum(listOfCosSimilarities)
end = time.time()

# print("Cosine Similarities of vectors: ", listOfCosSimilarities)
print("calculated ", len(batch_01.items), " embeddings")
print("sum of cosine similarities of vectors: ", sumAllCosSimilarities)
print("done in: ", end - start, "s")

