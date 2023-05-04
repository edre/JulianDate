# Julian Date complication for Android Wear OS

(A complication is like a widget for a watch face.)

The [Julian date](https://en.wikipedia.org/wiki/Julian_day) is a time format that is simply the number of days since noon UTC on Jan 1, 4713 BCE. This is a more human-centric version of the common computer format that counts the number of seconds since Jan 1 1970.

Example dates:
* 1705426.10000 - Assassination of Julius Caesar
* 2266295.90000 - Columbus lands in the Western Hemisphere
* 2440423.62240 - Apollo 11 moon landing
* 2460069.23991 - This repository uploaded to github

Breaking this down in to meaningful components:

`246----.-----` : Generation count. Each generation lasts 10,000 days, or 27.4 years: approximately one human generation. Thus, this repository was created 2 generations after the first moon landing and 76 generations after Julius Caesar.

`---0069.-----` : Day count.

`-------.2----` : Metric hours: each one lasts 100 metric minutes or about two and a half hours.

`-------.-39--` : Metric minutes: each one lasts 100 metric seconds or about a minute and a half.

`-------.---91` : Metric seconds: ticks once every 864ms.
