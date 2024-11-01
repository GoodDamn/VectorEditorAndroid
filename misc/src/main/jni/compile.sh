cd ..
echo "Copying files to JNI"
yes | cp -rf cpp/* jni/
echo "NDK Building"
ndk-build

echo "Setup jniLibs"
yes | rm -r jniLibs/*
yes | mv -f obj/local/* jniLibs/
yes | rm -r obj