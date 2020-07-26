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



col1 = dataset["pagreads"]
col2 = dataset["bufreads"]
col3 = dataset["block_size"]
col4 = dataset["CPU_iowait"]
col5 = dataset["nb_CPU"]

dataset["ga-max-size"] = col1
dataset["effective-cache-size"] = col2
dataset["java-pool-size"] = col3
#dataset["memory-target"] = col4
dataset["random-page-cost"] = col5

dataset["bufreads"] = col1
dataset["rdcached"] = col2
dataset["dskwrits"] = col3
dataset["pagwrits"] = col4
dataset["bufwrits"] = col5

dataset["bufwaits"] = col1
dataset["lokwaits"] = col2
dataset["lockreqs"] = col3
dataset["deadlks"] = col4
dataset["lchwaits"] = col5


dataset["shared-memory"] = col1
dataset["BandwidthI/O"] = col2
dataset["TR_Page_Disk"] = col3
dataset["latency_fetching"] = col4
dataset["read_bandwidth"] = col5


dataset["random-page-cost"] = col1
dataset["Number-disk-page-read"] = col2
dataset["Time_read_page_disk"] = col3
dataset["read_latency_disk"] = col4
dataset["read_bandwidth_disk"] = col5

#dataset["size_storage_structure"] = col3


dataset["Nbr_join_predicates"] = col4
dataset["Nbr_predicates_table"] = col1
dataset["Total_size_table"] = col2
#dataset["Number_tables_query"] = col3
#dataset["number_nested_subqueries"] = col4
#dataset[" number_selection_predicates"] = col5


#dataset["number_equality_selection_predicates"] = col4
#dataset["number_non-equality_selection_predicates"] = col1
#dataset["number_equijoin_predicates"] = col2
dataset["nbr_sort_columns"] = col3
dataset["nbr_aggregation_columns"] = col4




dataset = dataset.drop(["nb_CPU","Total_Threads","Cluster","CPU_user","CPU_sys","CPU_iowait","CPU_idle","dskreads","pagreads","bufreads","block_size"], axis = 1)

#Find relation between all data parameters
scatter_matrix_plot = scatter_matrix(dataset, figsize=(20, 20))
for ax in scatter_matrix_plot.ravel():
    ax.set_xlabel(ax.get_xlabel(), fontsize = 7, rotation = 45)
    ax.set_ylabel(ax.get_ylabel(), fontsize = 7, rotation = 90)
plt.show()

print('1111111111111111111111111111111111111111')

#Using Pearson Correlation find relation between various parameters
plt.figure(figsize=(10,10))
cor = dataset.corr()
sns.heatmap(cor, annot=False, cmap=plt.cm.Reds)
plt.savefig("Correlation22222.png",dpi=600)
plt.show()


f, ax = plt.subplots(figsize=(10, 6))
corr = dataset.corr()
hm = sns.heatmap(round(corr,2), annot=False, ax=ax, cmap="coolwarm",fmt='.2f',
                 linewidths=.05)
f.subplots_adjust(top=0.93)
t= f.suptitle('Cost Model parameters  Correlation Heatmap', fontsize=14)

