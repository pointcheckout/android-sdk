# PointCheckout Merchant SDK

These are the minimum required steps to use the PointCheckout SDK in your Android app. We assume that you are using Android Studio for your Android development. The minimum supported Android API level for the SDK is 16 (KitKat), however, setting the minimum Android API level to 26 (Pie) is recommended.

> The SDK uses Google's [SafetyNet API](https://developer.android.com/training/safetynet/attestation) for security, setting minimum Android API to lower than 26 will prevent it from functioning.

### Getting started

Add the SDK to your project:
 - Add the following to `YourProject/build.gradle`

```gradle
    allprojects {
        repositories {
            ...
            maven { url 'https://jitpack.io' }
            flatDir {
                dirs 'libs'
            }
        }
    }
```
 - Add the following dependency to `YourProject/app/build.gradle`

```gradle
  dependencies {
          implementation 'com.github.pointcheckout:merchant-android-sdk:v1.0.0'
  }
```

#### Add permissions
The PointCheckout SDK requires the following permissions. Please add them to your AndroidManifest.xml file if they are not already present:
```xml
<uses-permission android:name="android.permission.INTERNET"/>
```

#### Adding SafetyNet
Add Google's SafetyNet API to your `app/build.gradle`, follow [this guide](https://developers.google.com/android/guides/setup).

After these steps, rebuild your app and you are good to go!

### Using the SDK
#### Checkout request

Send new checkout request to [PointCheckout's API](https://www.pointcheckout.com/en/developers/api/api-integration) using endpoint `/mer/v1.2/checkouts` (check the [documentation](https://www.pointcheckout.com/en/developers/api/api-integration) for more details). The SDK needs two variables:

| Variable    | Mandatory | Description                                                                                                                                                   |
|-------------|-----------|---------------------------------------------------------------------------------------------------------------------------------------------------------------|
| redirectUrl | Yes       | Will be returned in the response body of the checkout request. This is the URL to the payment page of PointCheckout. The SDK accepts only PointCheckout URLs. |
| resultUrl   | No        | This is where you normally redirect the user on payment success or failure, for instance to mark the order as paid on payment success.                        |

#### Create PointCheckoutClient
Create an object of PointCheckoutClient:

```java
PointCheckoutClient pcClient = new PointCheckoutClient();
```
> Keep a reference of the created client to reuse the same instance

#### Initialize
Initialize the created `PointCheckoutClient` using:

```java
pcClient.initialize(context);
```
> Invoke initialize when the app starts because it needs 2-3 seconds. If the client is not initialized and pay is called, the client will call initialize internally before calling pay.

#### Payment submit

To submit a payment call the static `pay` method of the `PointCheckoutClient`:

```java
pcClient.pay(context, redirectUrl, resultUrl, new PointCheckoutEventListener() {
                @Override
                public void onPaymentCancel() {
                    System.out.println("!!PAYMENT CANCELLED");
                }

                @Override
                public void onPaymentUpdate() {
                    System.out.println("!!PAYMENT UPDATED");
                }
        });
```

| Parameter   | Description                                                      |
|-------------|------------------------------------------------------------------|
| context     | Current activity context                                         |
| redirectUrl | URL of the payment page, can be obtained from the API            |
| resultUrl   | URL that PointCheckout will call upon payment success or failure |
| listener    | Listener that will be called on payment update or cancellation   |

Calling the `pay` function will open a modal and the user will be able to login and complete the payment.

### Demo app
You can use our Demo app as an example of how to integrate our SDK on your application. you can access it from [here](https://github.com/pointcheckout/android-sdk-demo). You can import the example app to Android Studio to see how the SDK can be used.