# Event Booking

Welcome to Event Booking. This is a service to send invitations to a special two-day event in each country for partners in those countries. 

===========================

## Table of Contents
1. [Setup and Run](#setup-and-run)
2. [Algorithm Explanation](#algorithm-explanation) 

## Setup and Run

1. cd to the `event-booking` folder
2. Run `mvn clean install`
3. Run `mvn exec:java`

## Algorithm Explanation

1- Partition the partners by their country and map them to their country.

2- In each country, find the most partners who can make it for both days in the country

    2.1 Collect all available dates of all partners in the country
    2.2 Distinct and sort them
    2.3 Filter the partners who can make two continous days from the earlest date to the latest date in the country
    2.4 The largest size of the filtered partners are stored and put the `startDate` at the beginning of their `availableDates`
    
3- Based on the list of partners of each country, create the `Countries` which will be posted.
