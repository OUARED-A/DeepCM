#import required libraries
import pandas as pd
from pandas.plotting import scatter_matrix
from pandas import Grouper
import matplotlib.pyplot as plt
from matplotlib import pyplot
import seaborn as sns
from pandas import DataFrame
import numpy as np
from pandas import concat


#Function to find relation between all data parameters 
def scatter_plot (data):
    scatter_matrix_plot = scatter_matrix(dataset, figsize=(20, 20))
    for ax in scatter_matrix_plot.ravel():
        ax.set_xlabel(ax.get_xlabel(), fontsize = 7, rotation = 45)
        ax.set_ylabel(ax.get_ylabel(), fontsize = 7, rotation = 90)
    return scatter_matrix_plot

dataset = pd.read_csv('Dataset2.csv')


plt.figure(figsize=(12,4))
sns.boxplot(dataset['CPU_idle'], palette="Blues")
plt.show()


value1 = [290.02, 260.95, 270.53, 680.68, 440.13, 410.03,140.66,140.66,140.66,110.84,110.90,110.92,110.510,11.690,11.980,7.780,60.86,198.430,152.93]
value2=[2872.65,2668.42,2725.96,6799.06,4369.02,4062.38,1451.39,1451.17,1451.17,117.51,1177.66,118.33,113.72,1156.86,1186.31,770.18,679.34,1964.78,1513.72]
value3=[2901.67,2695.37,2753.49,6867.74,4413.15,4103.41,1466.05,1465.83,1465.83,1184.35,1189.56,1192.25,1151.23,1168.55,1198.29,777.96,686.202,19843.213,15292.648]
#value4=[59,73,70,16,81,61,88,98,10,87,29,72,16,23,72,88,78,99,75,30]
 
 
box_plot_data=[value1,value2,value3]
plt.boxplot(box_plot_data,patch_artist=True,labels=['I/O Cost','CPU Cost ','Total Cost'])
plt.savefig("box_plot_data2.png",dpi=600)

plt.show()

#colors = ['cyan', 'lightblue', 'lightgreen', 'tan']
