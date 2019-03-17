#H1 The Instruction Processing Microservice
The instruction-processing-service intends to offer distributed and parallel 
processing.


##H2 Description
This is a different concept in Microservices where the code to be executed 
by this Microservice is sent to the Microservice by an InstructionRequest
Multiple instructionScripts send in a InstructionRequest are each executed
thi their own ForthProcessor thread.


###H3 The Processor is a Virtual CPU with its MicroInstructions running Forth
A Processor is implemented which uses Forth as its Microinstruction Language

###H3 The Scripts executed 
The InstructionRequest send a List of instructionScripts to this service
to be executed by its Virtual CPU as "Forth Programs"

###H3 The CPU is Multythreaded
Virtual CPU spawns a ForthProcessor thread to run each 

##H2 I/O supported
The InstructionRequest has a Map : ioMap
and a List : ioVariables this identifies the variables used in the abover map

###H3 File I/O
You define File and Folder specifications which will be defined in the 
above variables : 
ioMap and ioVariables

###H3 Database I/o
You define Database connections , cridentials , and tables  which will 
be defined in the above variables : 
ioMap and ioVariables

###H3 Message Queues
You define Queue servers , queues , and credentials which will 
be defined in the above variables : 
ioMap and ioVariables

###H3 AES Encryption provided for I/O
AES Cyptography is provided by this service to decrypt the I/O variables

This is also provided for encrypting messages to be sent to Message Queues

Encryption key and SALT will be sent in the  to decrypt the values in the 

The InstructionRequest has these two fields used for decryption
aesEncryptionKey
aesSalt

They will not have key values but tell the instruction-processing-service 
which encryption Key and Salt to use.

