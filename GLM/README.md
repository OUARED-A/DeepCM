
Generalized-linear models (GLMs)
Installation
Install tensorflow, see below. Please use the pip installation if you are unsure.
Clone the GitHub repository of batchglm.
cd into the clone.
pip install -e .


Tensorflow installation
Tensorflow can be installed like any other package or can be compiled from source to allow for optimization of the software to the given hardware. Compiling tensorflow from source can significantly improve the performance, since this allows tensorflow to make use of all available CPU-specific instructions. Hardware optimization takes longer but is only required once during installation and is recommended if batchglm is used often or on large data sets. We summarize a few key steps here, an extensive up-to-date installation guide can be found here: https://www.tensorflow.org/install/

Out-of-the-box tensorflow installation
You can install tensorflow via pip or via conda.
