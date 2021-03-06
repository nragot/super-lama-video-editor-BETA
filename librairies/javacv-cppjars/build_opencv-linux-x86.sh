tar -xzvf opencv-$OPENCV_VERSION.tar.gz
mkdir opencv-$OPENCV_VERSION/build_linux-x86
cd opencv-$OPENCV_VERSION/build_linux-x86
CC="gcc -m32" CXX="g++ -m32" cmake -DBUILD_TESTS=OFF -DBUILD_PERF_TESTS=OFF -DBUILD_JASPER=ON -DBUILD_JPEG=ON -DBUILD_OPENEXR=ON -DBUILD_PNG=ON -DBUILD_TBB=ON -DBUILD_TIFF=ON -DBUILD_ZLIB=ON -DBUILD_opencv_java=OFF -DBUILD_opencv_python=OFF -DENABLE_PRECOMPILED_HEADERS=OFF -DWITH_1394=OFF -DWITH_FFMPEG=OFF -DWITH_GSTREAMER=OFF -DWITH_TBB=ON -DWITH_CUDA=OFF -DWITH_OPENCL=OFF ..
make -j4
LIBS="lib/libopencv*.so.${OPENCV_VERSION:0:3} lib/libtbb.so"
strip $LIBS
mkdir -p com/googlecode/javacv/cpp/linux-x86/
cp $LIBS com/googlecode/javacv/cpp/linux-x86/
jar cvf ../../opencv-$OPENCV_VERSION-linux-x86.jar com/
rm -Rf com/
cd ../../
