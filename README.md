
## 📋 Table of Contents

1. [About](#-finsight)
2. [Releases](#-release)
3. [Features](#-feature)
4. [Screenshots and demo video](#-screenshots-and-demo-video)
5. [Library](#-library)

---

# Finsight

Finsight is an AI driven application that aims to simplify financial planning by offering personalized investment advice built for Bangkit Academy Batch 2 2024 Capstone Project. The app uses machine learning to analyze user-provided data, such as income, investment amount, and risk tolerance (moderate, aggressive, or safe), and recommends optimal allocations in stocks, money markets, mutual funds, and other investment options.

By leveraging historical stock data and financial ratios, the app predicts stock trends and tailors suggestions for users. It helps individuals with surplus funds make informed decisions, encouraging smarter financial habits and long-term wealth building. We also provide a social feature to help users engage with one another and gather insight on financial topics. With a cloud-based architecture, the app ensures scalability and reliability, while mobile compatibility provides ease of access for users.

The app is build using Kotlin on Android Studio.

**Our team**
- [Willy](https://github.com/WhoIsLiLY) - Machine Learning
- [Darren](https://github.com/Cupcake-Legend) - Cloud Computing
- [Nathan](https://github.com/canonflow) - Machine Learning
- [Anton](https://github.com/antoniuskp) - Machine Learning
- [Randy](https://github.com/hyperneutr0n) - Cloud Computing
- [Edward](https://github.com/Cimedd) - Mobile Development

---

## 🚀 Releases

Find the latest releases [here](https://github.com/Cimedd/finsight-md/releases/tag/Finsight).

You can run the project on your own.
Follow these steps to clone and set up the project locally:

1. **Clone the Repository**

   Open your terminal or command prompt, navigate to the desired folder, and run:

   ```bash
   git clone https://github.com/Cimedd/finsight-md.git
   ```

2. **Open the Project**

   - Open the project in **Android Studio**.
   - Allow Gradle to sync all dependencies.

3. **Run the Application**

   - Connect an Android device via USB or use an emulator.
   - Click on the "Run" button in Android Studio.

4. **Configure the Backend** (Optional)

   - Ensure that the cloud services like Google Pub/Sub are set up correctly.
   - Replace any API keys or configurations in the `build.gradle` or `config` files if needed.

---

## Feature

Here the features available in finsight :
- Onboarding Page
- User authentication with email veerification
- Profile Risk Assessment
- Home Page :
   - Display user profile, suggested stocks, today's news, and list of stoc available for forecasting with their current market prices
   - Profile shows user followers, following, and their post history
- Feed Page : 
    - Show post created by finsight user
    - Show post created by other users followed by the User
    - Floating action button to add a post
    - Chat history where user can see anyone they've chatted with 
    - Comment on another user post
- Insight Page :
    - Cards showing general question about stocks
    - List of all news available
- Settings Page :
   - Customize your profile picture
   - Customize app theme
   - Turn on Notification

---

## 🖼 Screenshots and Demo Video

Check out the demo video of the project on [YouTube](https://youtu.be/OOyMRxYgSns).

Below are screenshots of the project design:

1.Onboarding 
![onbaording_1](Camera%20Roll/onboarding-1.jpg)
![onbaording_2](Camera%20Roll/onboarding-2.jpg)
![onbaording_3](Camera%20Roll/onboarding-3.jpg)

2.Login, Register, Risk Assessment
![login](Camera%20Roll/login.jpg)
![Register](Camera%20Roll/register.jpg)
![risk](Camera%20Roll/profile-risk.jpg)

3.Home
![Home](Camera%20Roll/home.jpg)
![forecast 1](Camera%20Roll/forecast-1.jpg)
![forecast 2](Camera%20Roll/forecast-1.jpg)

4.Feed
![feed](Camera%20Roll/feed.jpg)
![feed-add 1](Camera%20Roll/feed-add.jpg)
![feed-post 2](Camera%20Roll/feed-post-detail.jpg)
![chat](Camera%20Roll/chat-list.jpg)
![chat](Camera%20Roll/chat-detail.jpg)

5.Insight
![insight](Camera%20Roll/insight.jpg)
![insight-dialog](Camera%20Roll/insight-dialog.jpg)
![insight-news](Camera%20Roll/insight-news-detail.jpg)
![Notification](imCamera%20Rollage/notification.jpg)

---

## Library

This project uses the following libraries:

1. **Glide** - Image loading and caching
2. **Retrofit** - REST API client
3. **Google Cloud Pub/Sub** - Messaging and event streaming
4. **MPAndroidChart** - Graph and chart visualization
