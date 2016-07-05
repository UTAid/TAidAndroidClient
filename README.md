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
0.0.01


### Todos
##### Anya needs to assign more tasks!
 - Write Tests
 - Rethink Github Save
 - Add Code Comments
