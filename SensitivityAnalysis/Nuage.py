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

df = pd.read_csv('Dataset2.csv')

target = df.Total_Sys_Cost
df = df.drop(columns=['Total_Sys_Cost'])
# we use a for loop to plot our independent variables against our dependent one:
for col in df:
    sns.regplot(x=df[col], y=target, data=df, label=col)
    plt.ylabel('Total_Sys_Cost')
    plt.xlabel('')
    plt.legend()
    plt.tight_layout()
    if(col=='CPU_idle'):
        plt.show()
        plt.savefig("nuage.png",dpi=600)
    
