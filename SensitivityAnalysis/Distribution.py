#import required libraries
import pandas as pd
from pandas.plotting import scatter_matrix
from pandas import Grouper
import matplotlib.pyplot as plt
from matplotlib import pyplot
import seaborn as sns
import random
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

import matplotlib.pyplot as plt
import seaborn as sns
# use a for loop to plot each column from your dataframe
for column in dataset:
    sns.distplot(dataset[column], hist_kws=dict(color='plum',    edgecolor="k", linewidth=1))
    #lucky = dataset[column]
    print(column)
    if column == 'nb_CPU':
        plt.savefig("g0.png",dpi=600)
        plt.show()
    if (column == 'Total_Threads'):
        plt.savefig("g1.png",dpi=600)
        plt.show()
    if (column == 'Cluster'):
        plt.savefig("g2.png",dpi=600)
        plt.show()
    if (column == 'CPU_user'):
        plt.savefig("g3.png",dpi=600)
        plt.show()
    if (column == 'CPU_sys'):
        plt.savefig("g4.png",dpi=600)
        plt.show()
    if (column == 'CPU_iowait'):
        plt.savefig("g5.png",dpi=600)
        plt.show()
    if (column == 'CPU_idle'):
        plt.savefig("g6.png",dpi=600)
        plt.show()
    if (column == 'dskreads'):
        plt.savefig("g8.png",dpi=600)
        plt.show()
    if (column == 'pagreads'):
        plt.savefig("g9.png",dpi=600)
        plt.show()
    if (column == 'bufreads'):
        plt.savefig("g10.png",dpi=600)
        plt.show()
    if (column == 'block_size'):
        plt.savefig("g11.png",dpi=600)
        plt.show()