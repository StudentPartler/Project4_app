# Project4_app

Personal Safety App with Raspberry Pi Camera Surveillance System for Vulnerable 
(Wearable devices for Android)

Creating an Android app that allows vulnerable individuals to send distress signals with features like location sharing, an SOS button, 
and audio/video recording using Raspberry Pi involves both software and hardware components. 

Software Components:
1.	Android App Development:
  •	Start by setting up your Android app development environment using Android Studio or another preferred IDE.
  •	Design the user interface (UI) with user-friendly elements, including buttons for distress signals, location sharing, and audio/video recording.
  •	Implement user authentication and user profile management to allow users to add trusted contacts.
2.	Distress Signal Functionality:
  •	Create an SOS button within the app that, when pressed, triggers an emergency alert.
  •	When the SOS button is pressed, send a notification to the Raspberry Pi for further actions (explained later).
3.	Location Sharing:
  •	Use Android's location services (GPS or network-based) to obtain the user's current location.
  •	Implement a feature to share this location with trusted contacts via SMS, email, or in-app notifications.
4.	Audio/Video Recording:
  •	Integrate audio and video recording functionality within the app.
  •	Allow users to record audio or video clips that can be saved locally or transmitted to trusted contacts.
5.	Communication with Raspberry Pi:
  •	Establish communication between the Android app and the Raspberry Pi over a network connection. You can use sockets, APIs, or other methods to achieve this.
  •	When the SOS button is pressed or distress signals are initiated, send a command to the Raspberry Pi for further actions (e.g., activating cameras, sending alerts).

Hardware Components:
1.	Raspberry Pi:
  •	Set up your Raspberry Pi with a camera module (e.g., Raspberry Pi Camera Module) and an internet connection (Wi-Fi or Ethernet).
  •	Install necessary software on the Raspberry Pi for receiving commands from the Android app and controlling the camera module.
2.	Camera Module:
  •	Connect and configure the camera module on the Raspberry Pi.
  •	Implement the functionality to start recording video or capturing images when triggered by the Android app.
3.	Alert Mechanism:
  •	Integrate an alert mechanism on the Raspberry Pi to notify trusted contacts (e.g., via email or SMS) when an SOS signal is received.

Testing and Deployment:
  • Thoroughly test the Android app and Raspberry Pi setup to ensure that distress signals, location sharing, and audio/video recording work as intended.
  • Deploy the Android app on the users' devices and ensure they have Raspberry Pi access with the necessary permissions.
  • Provide clear instructions to users on how to use the app and set up the Raspberry Pi for their safety.
