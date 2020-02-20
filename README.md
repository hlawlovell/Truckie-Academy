# General Description

This native android application is an education platform that uses speech recognition and text to speech capabilities to deliver a hands-free learning experience. Lessons are delivered in a series of 'segments'. 
These segments each consist of a small lecture, followed by some questions which the users are prompted to answer to quiz their understanding. This application also allows users to view their progress
on lessons.

# Technology Stack

Programming language - Java

Backend - Firebase

Database - Firebase cloud firestore (NoSQL)

SDK - Android SDK 21

# External libraries used

Gson - Serialization/deserialization from .json file

Android speech - Speech recognition

Android speech tts - Text to speech

# Database schema

The database is a NoSQL DbaaS that is provided by Google's Firebase. Currently our schema is storing everything under the 'users' collection. /users/ contains a document for each registered user. It tracks
the total lessons they have completed, as well as their e-mail address for identification. Each user document also contains a collection called 'lessons'. These contain documents that record details 
about the lessons they have added to their device.

# Lessons are stored locally and in the database

Lessons as described above are persisted in .json files which are pulled from the Firebase cloud storage bucket in the '/lessons/' folder. When users add these lessons through MainActivity, they are 
stored in local storage to reduce the amount of database reads. As this happens, the lesson is recorded as a document in the database under /users/uid/lessons/lesson/. Currently the lesson 
document stores the user's top score, the time it was last updated, and the name of the lesson. Deletes made to application apply both locally and remotely on the database. Local storage directory
is retrievable with File dir = getFilesDir(). The required format for the lesson files can be seen here

```
{
    "name": "",
    
    "segments": [
        {
            "segmentText": "",
            "questions": [
                {
                    "question": "",
                    "answer": true
                }
            ]
               
        }

    ]
    
}
```


# TeachActivity

This is the activity where the lessons occur. TeachActivity is spawned by MainActivity, which passes a string of the lesson file name through an intent. The activity then reads the file from 
app storage, deserializing .json to Java object (Object is defined in the models folder) using the Gson library. Logic of the lesson flow is controlled using callback methods defined by both the 
android.speech.tts listener and the android.speech.listener, in conjunction with some  variables storing the current state of the lesson. currentState stores what stage of the lesson it is currently. 
0 represents the greeting stage, 1 represents the lecture stage, and 2 represents the questioning stage. The other variables keep track of what specific segment or question is currently being examined.


# Brief description of the activities

### MainActivity 
Home page, houses a menu button which navigates to other activities, and a floating action button that redirects to LessonAddActivity. Has a ListView that lists all lessons currently 
in app storage, onClick redirects to TeachActivity. 

### LoginActivity 
Uses firebase authentication

### TeachActivity 
See above.

### LessonAddActivity 
This activity uses a recyclerView to display lessons from the Firebase Cloud Storage bucket. It shows files from the /lessons/ folder. There is an onClick implementation that 
then downloads the files into app storage, and creates a reference in the database.

### ProgressActivity 
This activity uses a recyclerView to show the progress of a user. It uses data pulled from the database.

# Where to go from here

### Backend
Some suggestions of where to go from here. The backend is currently being managed by Firebase. That includes authentication, database and file storage. Perhaps a better long-term strategy would be
to develop one's own back-end using a framework such as Django or Ruby on Rails, Firebase was used as a backend was needed in a short time, but going with Django or something similar would offer better customizability and scaling.
Additionally, the use of an SQL database instead of the currently used NoSQL is much more suited to the type of data that we are collecting. 

### Lesson Design
Currently the lessons are designed in such a way that allows for teaching of basic concepts and knowledge. The structure of the lessons, or how the lesson is conducted requires a big overhaul and brainstorming
to get it to the level of teaching higher-level concepts.

### Design
The application currently has a basic level of design that could be improved with better iconography, better UX design, and responsiveness. 

### Data driven development
The database schema could be improved and altered to allow for the collection of more data from users. Pragmatic usages of this could be the tracking and analysis of which questions users tend to get wrong most often,
which questions they get right, where they are using the application, when. The list goes on. 

### Cross platform
Porting this application to IOS is a major point of consideration, not everyone has android phones.



# Website

https://truckieacademy.netlify.com/

# Scope

https://docs.google.com/document/d/1tuVRxZPONGGDo0hmFfQuteDjxidIdg4iYS-3lbxOBGY/edit?usp=sharing


