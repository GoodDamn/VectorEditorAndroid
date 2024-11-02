cd ..
echo "Copying files to JNI"
mkdir jni
yes | cp -rf cpp/* jni/
echo "NDK Building"
ndk-build

echo "Setup jniLibs"
yes | rm -r jniLibs
mkdir jniLibs
yes | mv -f libs/* jniLibs/
yes | rm -r obj
yes | rm -r libs
yes | rm -r jni