LICENSE = "NCSA"
LIC_FILES_CHKSUM = "file://LICENSE.TXT;md5=47e311aa9caedd1b3abf098bd7814d1d"

BRANCH = "llvm_release_130"
SRC_URI = "git://github.com/KhronosGroup/SPIRV-LLVM-Translator;protocol=https;branch=${BRANCH} \
            git://github.com/KhronosGroup/SPIRV-Headers.git;protocol=https;destsuffix=git/SPIRV-Headers;name=headers \
          "

PV = "13.0.0"
SRCREV = "7d3a83f6e81be9e13254e73edd4272fa96ed0d44"
SRCREV_headers = "ddf3230c14c71e81fc0eae9b781cc4bcc2d1f0f5"

S = "${WORKDIR}/git"

DEPENDS = "spirv-tools clang"

inherit cmake pkgconfig python3native

OECMAKE_GENERATOR = "Unix Makefiles"

# Specify any options you want to pass to cmake using EXTRA_OECMAKE:
EXTRA_OECMAKE = "\
        -DBUILD_SHARED_LIBS=ON \
        -DLLVM_SPIRV_BUILD_EXTERNAL=YES \
        -DCMAKE_BUILD_TYPE=Release \
        -DCMAKE_POSITION_INDEPENDENT_CODE=ON \
        -DCMAKE_SKIP_RPATH=ON \
        -DLLVM_EXTERNAL_LIT=lit \
        -DLLVM_INCLUDE_TESTS=ON \
        -Wno-dev \
        -DCCACHE_ALLOWED=FALSE \
        -DLLVM_EXTERNAL_SPIRV_HEADERS_SOURCE_DIR=${S}/SPIRV-Headers \
"

do_compile:append() {
    oe_runmake llvm-spirv
}

do_install:append() {
    install -Dm755 ${B}/tools/llvm-spirv/llvm-spirv ${D}${bindir}/llvm-spirv
}

BBCLASSEXTEND = "native nativesdk"