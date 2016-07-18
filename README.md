# TAidAndroidClient

## How to run it:
#### Set up the Django backend
>  Set up the [Django backend](https://github.com/UTAid/TAidBackend/tree/cors)
>> Set up the TAid Django backend in a seperate directory (use the cors branch! See below). Run migrations if needed, and create a superuser ```python manage.py createsuperuser```. We will be using this user to login. Finally, run the server
```python manage.py runserver 8000```


#### Set up the Android Client 
##### After running the server successfully and you are able to connect to it properly, you can use an external android device, or an emulator and run the code using Android Studio. (this is just for the current stage of development.. apk will be done soon)
  - If the project is opened properly using AndroidStudio, wait for the dependencies to install and for the gradle to build
  - When all of that is done, the program should be able to be started using the green play button at the top
  - Either select a device from the virtual devices or put your android device on developer mode and select it from the Device manager menu
  - App should instantly run

### Version
0.0.02


### Todos
#### Anya needs to assign more tasks!
 - Get Offline mode working
 - Research similar workflow apps, and see what we could use 
 - class list, best way to display it
    - last name, first name: click to display fully
    - should not be editable
    - be able to add and flag "hey you just added ----"
    - 
 - tutorail instruction are just text, want to display it
    - enable highlighting 
    - enable adding comments
      - "something was done", "something was not done" 
  - make it modular!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    -example: if i want to edit a teams mark, simply click on one of the members and be able to edit the team's mark
