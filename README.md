# PC Android SDK

This is the Android SDK of PointCheckout. You can read more about PointCheckout at [pointcheckout.com](https://www.pointcheckout.com/).

## Quick start

### Example app
There is an example app at [TDODO: addurl]. You can import the example app to [Android Studio](https://developer.android.com/studio) to see how the SDK can be used.

### Getting started

These are the minimum required steps to use the PointCheckout SDK in your Android app. We assume that you are using Android Studio for your Android development. The minimum supported Android API level for the SDK is 16 (KitKat), however, setting the minimum Android API level to 26 (Pie) is recommended.

 > The SDK uses Google's [SafetyNet API](https://developer.android.com/training/safetynet/attestation) for security, setting minimum Android API to lower than 26 will prevent it from functioning.

#### Add the SDK to your project

 - Download the `.aar` (Android Archive Library) file from here [TODO: addUrl]
 - Create `libs` directory and add the downloaded file to it.
 - Add the following to `build.gradle`
 
 ```
repositories {
    mavenCentral()
    flatDir {
        dirs 'libs'
    }
}
 ```
 - Add the dependency to `app/build.gradle` 

```
dependencies {
    ...
    implementation(name:'pc-android-sdk', ext:'aar')
}
```

#### Add permissions

The PointCheckout SDK requires the following permissions. Please add them to your `AndroidManifest.xml` file if they are not already present:

```
<uses-permission android:name="android.permission.INTERNET"/>
```

#### Adding SafetyNet

Add Google's SafetyNet API to your `app/build.gradle`, follow [this guid](https://developers.google.com/android/guides/setup). 

After these steps, rebuild your app and you are good to go!

## Using the SDK

First you will need a `checkoutKey` which can be obtained via PointCheckout's API.

### Create PointCheckoutClient

Create an object of PointCheckoutClient:

<table>
<tr>
<td>
<b>Production environment with auto dismiss</b>
</td>
</tr>
<tr>
<td>

```java
PointCheckoutClient pcClient = new PointCheckoutClient();
```
</td>
</tr>

<tr>
<td>
<b>Production environment without auto dismiss</b>
</td>
</tr>
<tr>
<td>

```java
PointCheckoutClient pcClient = new PointCheckoutClient(false);
```
</td>
</tr>

<tr>
<td>
<b>Test environment without auto dismiss</b>
</td>
</tr>
<tr>
<td>

```java
PointCheckoutClient pcClient = new PointCheckoutClient(Environment.TEST, true);
```
</td>
</tr>
<table>
    
<tr>
<td>
<b>Parameter</b>
</td>
<td>
<b>Default</b>
</td>
<td>
<b>Description</b>
</td>
</tr>
<tr>
<td>
Environment
</td>
<td>
Environment.PRODUCTION
</td>
<td>
Specifies the environment of the app, use Environment.TEST for testing purposes.
</td>
</tr>

<tr>
<td>
autoDismiss
</td>
<td>
true
</td>
<td>
Closes the modal on payment success or failure. 
Regardless of autoDismiss value, the onDismiss method of PointCheckoutEventListener will be called on payment success or failure.
</td>
</tr>

</table>

> Create a single instance of `PointCheckoutClient` and re-use that instance each time you want to checkout.

### Initialize

Initialize `PointCheckoutClient` using:

```java
PointCheckoutClient pcClient = new PointCheckoutClient();
pcClient.initialize(context);
```
> Invoke `initialize` when the app starts because it needs 2-3 seconds. If the client is not initialized and checkout is performed, the client will call `initialize` internally before checking out.

### Payment submit

To submit a payment call the `pay` method of the `PointCheckoutClient`:

```java
pcClient.pay(context, checkoutKey, new PointCheckoutEventListener() {
  @Override
  public void onPaymentCancel() {
    // payment was cancelled
  }
  @Override
  public void onPaymentUpdate() {
    // check the status of the payment and act accordingly
  }
});
```

Calling the `pay` method will open a modal and the user will be able to login and complete the payment.

## Licence
Copyright © PointCheckout.com. All Rights Reserved.