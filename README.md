
# react-native-ccs-safeareaview

## Getting started

`$ npm install react-native-ccs-safeareaview --save`

### Mostly automatic installation

`$ react-native link react-native-ccs-safeareaview`

### Manual installation


#### iOS

1. In XCode, in the project navigator, right click `Libraries` ➜ `Add Files to [your project's name]`
2. Go to `node_modules` ➜ `react-native-ccs-safeareaview` and add `RNCcsSafeareaview.xcodeproj`
3. In XCode, in the project navigator, select your project. Add `libRNCcsSafeareaview.a` to your project's `Build Phases` ➜ `Link Binary With Libraries`
4. Run your project (`Cmd+R`)<

#### Android

1. Open up `android/app/src/main/java/[...]/MainActivity.java`
  - Add `import com.reactlibrary.RNCcsSafeareaviewPackage;` to the imports at the top of the file
  - Add `new RNCcsSafeareaviewPackage()` to the list returned by the `getPackages()` method
2. Append the following lines to `android/settings.gradle`:
  	```
  	include ':react-native-ccs-safeareaview'
  	project(':react-native-ccs-safeareaview').projectDir = new File(rootProject.projectDir, 	'../node_modules/react-native-ccs-safeareaview/android')
  	```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
  	```
      compile project(':react-native-ccs-safeareaview')
  	```

#### Windows
[Read it! :D](https://github.com/ReactWindows/react-native)

1. In Visual Studio add the `RNCcsSafeareaview.sln` in `node_modules/react-native-ccs-safeareaview/windows/RNCcsSafeareaview.sln` folder to their solution, reference from their app.
2. Open up your `MainPage.cs` app
  - Add `using Ccs.Safeareaview.RNCcsSafeareaview;` to the usings at the top of the file
  - Add `new RNCcsSafeareaviewPackage()` to the `List<IReactPackage>` returned by the `Packages` method


## Usage
```javascript
import RNCcsSafeareaview from 'react-native-ccs-safeareaview';

// TODO: What to do with the module?
RNCcsSafeareaview;
```
  