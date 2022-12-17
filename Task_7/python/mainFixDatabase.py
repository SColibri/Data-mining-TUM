
from embeddingStructure import embeddingStructure
from tqdm import tqdm

filename = "/home/ubuntu/sampled_embeddings.csv"
fileDB = open(filename, 'r')

fileToSave = "/home/ubuntu/sampled_embeddings_reduced.csv"
fileDBTS = open(fileToSave, 'w')

# check for hadoop safe mode?
# hdfs dfsadmin -safemode leave
# import 100,000 entries as a test database
for i in tqdm(range(100000)):
    eStruct = embeddingStructure()
    line = fileDB.readline()
    eStruct.load(line)
    csvFormat = eStruct.get_csv()
    fileDBTS.write(csvFormat + '\n')

fileDB.close()
