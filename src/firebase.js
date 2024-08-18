import { initializeApp } from 'firebase/app';
import { getAuth, GoogleAuthProvider, FacebookAuthProvider } from 'firebase/auth';
import { getAnalytics } from "firebase/analytics";

const firebaseConfig = {
    apiKey: "AIzaSyC7n5POZSpMsGvHD6YtJAPyZNVEFRM0kf0",
    authDomain: "intuiteassessment.firebaseapp.com",
    projectId: "intuiteassessment",
    storageBucket: "intuiteassessment.appspot.com",
    messagingSenderId: "717577745496",
    appId: "1:717577745496:web:0a06471a756a3e9ec2062d",
    measurementId: "G-090YDR3Z1W"
};

const app = initializeApp(firebaseConfig);

export const analytics = getAnalytics(app);
export const auth = getAuth(app);
export const googleProvider = new GoogleAuthProvider();
export const facebookProvider = new FacebookAuthProvider();
