

# importing the libraries
import numpy as np
import pandas as pd
import matplotlib.pyplot as plt
import csv
from pandas import DataFrame
import datetime
import time
import string


print('Start Program.............')

############################# Load and Preprocessing #################
# loading the data training
data = pd.read_csv('training.csv')
data222 = pd.read_csv('training.csv')

# loading the data test
test = pd.read_csv('test.csv')
#test555 = pd.read_csv('test.csv')

y_test_pred =test.iloc[:,-1]


Result = pd.read_csv('sample_submission.csv')

val=0

X = data.iloc[:,:].values
target = data["FraudResult"]
#target = data.iloc[: , -1].values

y =data.iloc[:,-1]



##### Adding the transaction duration




# encoded CustomerId
#string = "freeCodeCamp"
#print(string[2:6])


# encoded ProviderId
ProviderId =data["ProviderId"].values
for i in range(len(ProviderId)):
    s = ProviderId[i]
    s = s[11:len(s)]
    ProviderId[i]=s

    #print(s)
data = data.drop(["ProviderId"], axis = 1)
# encoded CustomerId
CustomerId =data["CustomerId"].values
for i in range(len(CustomerId)):
    s = CustomerId[i]
    s = s[11:len(s)]
    CustomerId[i]=s

  #  print(s)
    
data = data.drop(["CustomerId"], axis = 1)



# encoded ProductId
ProductId =data["ProductId"].values
for i in range(len(ProductId)):
    s = ProductId[i]
    s = s[10:len(s)]
    ProductId[i]=s

    #print(s)
    
data = data.drop(["ProductId"], axis = 1)



# encoded ChannelId
ChannelId =data["ChannelId"].values
for i in range(len(ChannelId)):
    s = ChannelId[i]
    s = s[10:len(s)]
    ChannelId[i]=s

    #print(s)
    
data = data.drop(["ChannelId"], axis = 1)






#ProductCategory
ProductCategory =data["ProductCategory"].values
for i in range(len(ProductCategory)):
    s =ProductCategory[i]
    if s =="airtime":
        ProductCategory[i]= 1
    if s =="financial_services":
        ProductCategory[i]= 2
        
    if s =="utility_bill":
        ProductCategory[i]= 3

    if s =="movies":
        ProductCategory[i]= 4

    if s =="data_bundles":
        ProductCategory[i]= 5

    if s =="tv":
        ProductCategory[i]= 6


    if s =="data_bundles":
        ProductCategory[i]= 7
    if s =="ticket":
        ProductCategory[i]= 8
        
    #print(ProductCategory[i])
    
data = data.drop(["ProductCategory"], axis = 1)





#encoded date

#from pandas.tseries.offsets import MonthEnd
#from datetime import timedelta, datetime, tzinfo, timezone
#Split the times into different features
#df = data["TransactionStartTime"].values
#df_travel_date=data["TransactionStartTime"].values
#df_year=data["TransactionStartTime"].values
#df_day=data["TransactionStartTime"].values
#df_month=data["TransactionStartTime"].values
#df_hour=data["TransactionStartTime"].values
#df_minute=data["TransactionStartTime"].values
#df_dayofweek=data["TransactionStartTime"].values

#for i in range(len(df)):
 #   df_travel_date[i] = pd.to_datetime(df[i], utc=True)
  #  print(df_travel_date[i])
    #df_year[i] = df[i].dt.year
    #df_hour[i] = df[i].dt.hour
    #df_minute[i] = df[i].dt.minute
    #df_day[i] = df[i].dt.day
    #df_month[i] = df[i].dt.month
    #df_dayofweek[i] = df[i].dt.dayofweek






#df['travel_date'] = pd.to_datetime(df['TransactionStartTime'], infer_datetime_format=True)
#df['travel_date'] =pd.to_datetime('2017-01-01T15:00:00-05:00', utc=True)
#df['travel_date'] =pd.to_datetime(df[3], utc=True)

#df['travel_time'] = pd.to_datetime(df['TransactionStartTime'])
#df['year'] = df['TransactionStartTime'].dt.year
#df['month'] = df['TransactionStartTime'].dt.month
#df['day'] = df['TransactionStartTime'].dt.day
#df['day_of_week'] = df['TransactionStartTime'].dt.dayofweek
#df['hour'] = df['TransactionStartTime'].dt.hour
#df['minute'] = df['TransactionStartTime'].dt.minute
    


#data["travel_date"] = df['travel_date']
#data["travel_time"] = df['travel_time']
#data["year"] = df['year']
#data["month"] = df['month']
#data["day"] = df['day']
#data["day_of_week"] = df['day_of_week']
#data["hour"] = df['hour']
#data["minute"] = df['minute']




# droping the data we don't need
# Drop unnecessary columns for train
#TransactionId,BatchId,AccountId,SubscriptionId,CurrencyCode
    
data["ProviderId"] = ProviderId

data["CustomerId"] = CustomerId
data["ProductId"] = ProductId

data["ChannelId"] = ChannelId
data["ProductCategory"] = ProductCategory
    
data = data.drop(["TransactionId", "BatchId" ,"AccountId","SubscriptionId" , "CustomerId" , "CurrencyCode","ProviderId","ProductId","ProductCategory","ChannelId","TransactionStartTime","FraudResult"], axis = 1)



#data = data.drop(["TransactionId","CountryCode","AccountId", "BatchId","SubscriptionId", "CurrencyCode","TransactionStartTime","FraudResult"], axis = 1)

#   add column



Xafterdrop = data.iloc[:,:].values




# converting to numpy arrays

Xnew = data.iloc[:,:].values
y = target.iloc[:].values






# Encoding The data
from sklearn.preprocessing import LabelEncoder
label = LabelEncoder()

Xnew[:,0] = label.fit_transform(Xnew[:,0])
Xnew[:,1] = label.fit_transform(Xnew[:,1])
Xnew[:,2] = label.fit_transform(Xnew[:,2])
Xnew[:,3] = label.fit_transform(Xnew[:,3])
#Xnew[:,4] = label.fit_transform(Xnew[:,4])
#Xnew[:,5] = label.fit_transform(Xnew[:,5])
#Xnew[:,6] = label.fit_transform(Xnew[:,6])
#Xnew[:,7] = label.fit_transform(Xnew[:,7])


y = label.fit_transform(y)



#numbers=[1,2,3,4,5,6,7,8,9,10,11,12]
#for i in numbers:
#Xnew[:,i] = label.fit_transform(Xnew[:,i])


 

# Standerdizing
from sklearn.preprocessing import StandardScaler
scaler = StandardScaler()
Xnew = scaler.fit_transform(Xnew)


print('**********************************************')

      
      
# trying PCA
from sklearn.decomposition import PCA
pca = PCA(4)
red_X = pca.fit_transform(Xnew)
ratio = pca.explained_variance_ratio_
y = label.fit_transform(y)



# Plotting The data
plt.scatter(red_X[:,0] , red_X[:,1] , c = y )
plt.legend()
plt.show()





# spiting the data test train split
from sklearn.model_selection import train_test_split
x_train , x_test , y_train , y_test = train_test_split(red_X,y , test_size = 0.2 , random_state = 0)



############################## Machine Learning Models #################################
from sklearn.metrics import r2_score
from sklearn.metrics import confusion_matrix

from sklearn.naive_bayes import GaussianNB


############# Naive Bayes
print('Start.............')

classifier = GaussianNB()
classifier.fit(x_train,y_train)
y_pred = classifier.predict(x_test)
print('The score For Naive Bayes: ' , r2_score(y_test , y_pred))
print('The accuracy for NB is ', (y_pred == y_test).mean())
cm = confusion_matrix(y_test,y_pred)
print('Confusion Matrix is :', cm)
print('######################################')
      
      
      
  ############# use  Naive Bayes model
  
  
  
  
# droping the data we don't need
  
 	# Drop for test
	#datedTest.drop(['car_type', 'travel_to', 'travel_date', 'travel_time'], axis=1, inplace=True) 
    
    

# encoded ProviderId
ProviderId =test["ProviderId"].values
for i in range(len(ProviderId)):
    s = ProviderId[i]
    s = s[11:len(s)]
    ProviderId[i]=s

    #print(s)
test = test.drop(["ProviderId"], axis = 1)
# encoded CustomerId
CustomerId =test["CustomerId"].values
for i in range(len(CustomerId)):
    s = CustomerId[i]
    s = s[11:len(s)]
    CustomerId[i]=s

  #  print(s)
    
test = test.drop(["CustomerId"], axis = 1)



# encoded ProductId
ProductId =test["ProductId"].values
for i in range(len(ProductId)):
    s = ProductId[i]
    s = s[10:len(s)]
    ProductId[i]=s

    #print(s)
    
test = test.drop(["ProductId"], axis = 1)



# encoded ChannelId
ChannelId =test["ChannelId"].values
for i in range(len(ChannelId)):
    s = ChannelId[i]
    s = s[10:len(s)]
    ChannelId[i]=s

    #print(s)
    
test = test.drop(["ChannelId"], axis = 1)






#ProductCategory
ProductCategory =test["ProductCategory"].values
for i in range(len(ProductCategory)):
    s =ProductCategory[i]
    if s =="airtime":
        ProductCategory[i]= 1
    if s =="financial_services":
        ProductCategory[i]= 2
        
    if s =="utility_bill":
        ProductCategory[i]= 3

    if s =="movies":
        ProductCategory[i]= 4

    if s =="data_bundles":
        ProductCategory[i]= 5

    if s =="tv":
        ProductCategory[i]= 6


    if s =="data_bundles":
        ProductCategory[i]= 7
    if s =="ticket":
        ProductCategory[i]= 8
        
    #print(ProductCategory[i])
    
test = test.drop(["ProductCategory"], axis = 1)

test["ProviderId"] = ProviderId

test["CustomerId"] = CustomerId
test["ProductId"] = ProductId

test["ChannelId"] = ChannelId
test["ProductCategory"] = ProductCategory
test = test.drop(["TransactionId", "BatchId" ,"AccountId","SubscriptionId" , "CustomerId" , "CurrencyCode","ProviderId","ProductId","ProductCategory","ChannelId","TransactionStartTime"], axis = 1)


# converting to numpy arrays
testnew = test.iloc[:,:].values

# Encoding The data
from sklearn.preprocessing import LabelEncoder
label = LabelEncoder()
testnew[:,0] = label.fit_transform(testnew[:,0])
testnew[:,1] = label.fit_transform(testnew[:,1])
testnew[:,2] = label.fit_transform(testnew[:,2])
testnew[:,3] = label.fit_transform(testnew[:,3])
# Standerdizing
from sklearn.preprocessing import StandardScaler
scaler = StandardScaler()
testnew = scaler.fit_transform(testnew)
# trying PCA
from sklearn.decomposition import PCA
pca = PCA(4)
red_test = pca.fit_transform(testnew)
ratio = pca.explained_variance_ratio_


# apply model


y_test_pred = classifier.predict(red_test)

    

        

 
print('End1.............')


      
      
 # travail qui reste 
# 1 encodage des variables 
# 2 resultat dans fichicre submission 
#  3 Trans toutes les colonnes    


# Write to files
istColumn = Result["TransactionId"]
Result["TransactionId"] = istColumn
Result["FraudResult"] = y_test_pred
 
cols = ['TransactionId', 'FraudResult']
finalTrain = Result[cols]
finalTrain.to_csv('sample_submission.csv', index=False) 
    
    
    


print('End2.............')
