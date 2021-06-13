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
          implementation 'com.github.pointcheckout:android-sdk:v1.0.0'
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

The bellow diagram shows how the payment process works:
![][img_sequence]

[img_sequence]: https://static.staging.pointcheckout.com/17a04be6556a64cc/original

#### Checkout request

Send new checkout request to [PointCheckout's API](https://www.pointcheckout.com/en/developers/api/api-integration) using endpoint `/mer/v1.2/checkouts` (check the [documentation](https://www.pointcheckout.com/en/developers/api/api-integration) for more details). 

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

| Parameter   | Description                                                         |
|-------------|---------------------------------------------------------------------|
| context     | Current activity context                                            |
| redirectUrl | This URL is included in the checkout response from PointCheckout API|
| resultUrl   | The same URL passed to PointCheckout API when creating the checkout |
| listener    | Listener that will be called on payment update or cancellation      |

Calling the `pay` function will open a modal and the user will be able to login and complete the payment.

#### PointCheckoutEventListener

The event listener has two callbacks, `onPaymentCancel` and `onPaymentUpdate`.

`onPaymentCancel` will only be called if the user closes the modal by clicking on close button.

`onPaymentUpdate` will be called whenever the checkout status is updated (paid, cancelled, failed .etc). When this callback is invoked you should call PointCheckout API to fetch the new status of the checkout.


### Demo app
You can use our Demo app as an example of how to integrate our SDK on your application. you can access it from [here](https://github.com/pointcheckout/android-sdk-demo). You can import the example app to Android Studio to see how the SDK can be used.
