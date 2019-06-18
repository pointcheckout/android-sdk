<img src="https://www.pointcheckout.com/img/logo/logo.svg" width=100/>

# PC Android SDK

This is the Android SDK of PointCheckout. You can read more about PointCheckout at [pointcheckout.com](https://www.pointcheckout.com/).

## Quick start

### Example app
There is an example app at [TDODO: addurl]. You can import the example app to [Android Studio](https://developer.android.com/studio) to see how the SDK can be used.

### Getting started

These are the minimum required steps to use the PointCheckout SDK in your Android app. We assume that you are using Android Studio for your Android development. The minimum supported Android API level for the SDK is 28 (Pie).

#### Add the SDK to your project

Add the following to your `build.gradle` file:

```

allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }

    }
}

dependencies {
    ...
    implementation 'com.github.AbdullahAsendar:pc-android-sdk:0.01'
}
```

#### Add permissions

The PointCheckout SDK requires the following permissions. Please add them to your `AndroidManifest.xml` file if they are not already present:

```
<uses-permission android:name="android.permission.INTERNET"/>
```

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

### Payment submit

To submit a payment call the `pay` method of the `PointCheckoutClient`:

```java
pcClient.pay(context, checkoutKey, new PointCheckoutEventListener() {
  @Override
  public void onDismiss() {
    // check the status of the payment and act accordingly
  }
});
```

Calling the `pay` method will open a modal and the user will be able to login and complete the payment.

## Licence
Copyright © PointCheckout.com. All Rights Reserved.
