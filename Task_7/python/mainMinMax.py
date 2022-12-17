from embeddingStructure import embeddingStructure
from batch import batch
from tqdm import tqdm

filename = "/home/ubuntu/sampled_embeddings_reduced.csv"
fileDB = open(filename, 'r')

minVals = [float() for _ in range(1024)]
maxVals = [float() for _ in range(1024)]

for i in range(1024):
    minVals[i] = 1.0
    maxVals[i] = -1.0
    
for i in tqdm(range(100000)):
    eStruct = embeddingStructure()
    eStruct.load(fileDB.readline())

    for j in range(1024):
        if minVals[j] > eStruct.data[j]:
            minVals[j] = float(eStruct.data[j])

        if maxVals[j] < eStruct.data[j]:
            maxVals[j] = float(eStruct.data[j])

converted_list = [str(element) for element in minVals]
print("Min:" + ",".join(converted_list))

converted_list = [str(element) for element in maxVals]
print("Max:" + ",".join(converted_list))
