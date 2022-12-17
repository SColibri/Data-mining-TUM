# ------------------------------------------------
# Code written for DataMining - TUM 10/12/2022
# Class structure used for loading data from the
# embedding database.
# ------------------------------------------------
import numpy as np

# Data structure for the embedding data
class embeddingStructure():

    # Constructor init
    def __init__(self):
        self.expectedSize = 1024
        self.Tag = ""
        self.AFDB = ""
        self.UA = ""
        self.UI = ""
        self.OS = ""
        self.OX = ""
        self.GN = ""
        self.data = []
    
    # Load from csv string into structure
    def load(self, inputString):
        arrValue = inputString.split(',')
        
        tagIndex = len(arrValue) - self.expectedSize
        for index in range(tagIndex):
            self.Tag += arrValue[index] + " "

        self.load_fromTag("AFDB")
        self.load_fromTag("UA")
        self.load_fromTag("UI")
        self.load_fromTag("OS")
        self.load_fromTag("OX")
        self.load_fromTag("GN")
        
        self.data = np.tile(np.float32(0), len(arrValue) - tagIndex)

        if (len(self.data) == 0):
            stophere = 1
        for index in range(tagIndex, len(arrValue)):
            self.data[index - tagIndex] = float(arrValue[index])
    # Load data into specific tag items (optional process)
    def load_fromTag(self, tagName):

        # Check if attribute name exists
        if not hasattr(self, tagName):
            return

        # Check if tag contains said tagName
        if tagName in self.Tag:
            index_01 = self.Tag.index(tagName)

            try:
                index_02 = self.Tag.index("=", index_01 + len(tagName) + 1) - 2
                setattr(self, tagName, self.Tag[index_01 + 5:index_02])
            except:
                setattr(self, tagName, self.Tag[index_01 + 5:])
            
        else:
            setattr(self, tagName, "N/A")
            
    def get_csv(self):
        csv_string = self.Tag.replace(",", " ")
        converted_list = [str(element) for element in self.data]
        csv_string += "," + ",".join(converted_list)
        return csv_string

