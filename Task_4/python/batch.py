from embeddingStructure import embeddingStructure
import logging

class batch():

    def __init__(self):
        self.items = []
    
    def load(self, filename, startLine, endLine):
        fileDB = open(filename, 'r')
        limitSize = int(endLine - startLine)

        # Initialize array
        self.items = [embeddingStructure() for _ in range(limitSize + 1)]
        logging.info("batch    : starting section")

        # Skip to starting line
        for index in range(int(startLine)):
            fileDB.readline()

        logging.info("batch    : line found")
        for index in range(limitSize):
            # Get next line from file
            line = fileDB.readline()
            self.items[index].load(line)
        
        fileDB.close()
        logging.info("batch    : section done")