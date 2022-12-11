import threading
import time
import logging
import pytz
import pandas as pd

from embeddingStructure import embeddingStructure
from batch import batch

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

print("---- batch 02 -----")
y = threading.Thread(target=batch_01.load, args=(filename, 1E5, 1.1E5))
y.start()

print("---- batch 01 -----")
x = threading.Thread(target=batch_01.load, args=(filename, 1E4, 2E4))
x.start()

x.join()
y.join()

logging.info("Main    : all done")
