# Stocks

Stocks is a console based java application.  The application takes a file trades.json as an input.
It produces "OHLC" (Open/High/Low/Close) time series data.


## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Prerequisites

Maven 3.x or higher add to the path.
Java 1.8 and higher and added to the path.
Git installed and added to the path.
The trades.json file copied onto the local directory



### Installing

Step 1:
Copying the code to your machine

Open linus command line.
git clone https://github.com/ravi273/stocks
This step will copy the code to stocks directory on the machine

Step 2:

Chnaing the config.

cd stocks/src/main/resources
Open config.properties in your favourite text editor.

This file has three properties
filepath  : Path to be changed to point to trades.json sample vale :/home/Downloads/trades-data/reduced.json
interval  : Interval over which the OHLC data will be aggregated in seconds. sample vale :5
symbol    : share symbol for processin. sample vale : XXBTZUSD)

Change the value in your favourite text editor.


Step 3:
Generating the jar file.

go back to the stocks folder in the command line

Type
mvn assembly:assembly -DdescriptorId=jar-with-dependencies
This step will generate the jar(stocks-1.0.0-jar-with-dependencies.jar) in the target folder.

Step 4:

cd target  (move to target folder)
java -cp "stocks-1.0.0-jar-with-dependencies.jar" com.projects.App (Runn the applcation)

You should see the output similar to this


Trade Streaming started
{"o":0.01947,"h":6538.8,"l":0.01947,"c":6538.2,"volume":6.598558000000001,"event":"ohlc_notify","symbol":"XXBTZUSD","bar_num":1}





## Running the tests

move to stocks folder

mvn test 



## Built With


* [Maven](https://maven.apache.org/) - Dependency Management



## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details


