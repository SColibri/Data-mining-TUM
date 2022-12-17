import matplotlib.pyplot as plt

# Make up 2 metoids coordinates and assign data points to them
# ############################################################
metoid_1 = [2,2]
metoid_2 = [3,5]
data_points_metoid_1 = [[1,1],[1,2],[3,2],[3,4]]
data_points_metoid_2 = [[6,6],[4,6],[6,3]]

fig, ax = plt.subplots()
# ############################################################

# Creates lines between each data point and assosciated metoid
# ############################################################
for point in data_points_metoid_1:
    x_values = [metoid_1[0] , point[0]]
    y_values = [metoid_1[1], point[1]]
    plt.plot(x_values, y_values, 'bx', linestyle='-')
for point in data_points_metoid_2:
    x_values = [metoid_2[0] , point[0]]
    y_values = [metoid_2[1], point[1]]
    plt.plot(x_values, y_values, 'gx', linestyle='-')
# ############################################################

# Circle the metoids with a red circle to highlight the center of the cluster
# ############################################################################
for metoid in [metoid_1, metoid_2]:
    circle = plt.Circle((metoid[0],metoid[1]), 0.25, color='red', fill=True )
    ax.add_patch(circle)
# ###########################################################################
    
plt.show()

# Cosine similarity
# ##################
vecA = [ [1],[1],[0] ]
vecB = [ [0],[1],[0] ]

a = np.array(vecA)
b = np.array(verB)

