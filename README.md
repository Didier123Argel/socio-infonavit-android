# socio-infonavit-android


<image hspace="20" src="https://github.com/Didier123Argel/socio-infonavit-android/blob/main/app/src/main/res/drawable-xxxhdpi/drawer_logo.png" width=40% height=40%/>

### Steps to build the project:

### To add your API key to this project:

1. Create a file ./secure.properties in the project root directory
2. Add these lines, where YOUR_API_KEY is your API key: ASYMMETRIC_ENCRYPTION=YOUR_API_KEY
3. Add your public key, no extra spaces or characters


### Change production apis

- In the build.gradle(:app) file you will see two sections (dev and prod). You just have to change the url you need in any of the sections.

```
productFlavors {
        dev {
            dimension "infonavit"
            versionNameSuffix "-dev"
            ext {
                buildConfigField "String", "API_BASE_URL", "\"https://qa-api.socioinfonavit.com/api/\""
            }
        }

        prod {
            dimension "infonavit"
            versionNameSuffix ""
            ext {
                buildConfigField "String", "API_BASE_URL", "\"https://qa-api.socioinfonavit.com/api/\""
            }
        }
    }
```


# Contact

If you have any problems with this, you can send me an email: `didier.chan@dacodes.com.mx`.
