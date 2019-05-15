Few Test cases for /Inventory/Ad endpoint in the new system
verify login for both systems - new & legacy system with response code 200
verify headers and input data (payload)
check response code (200)
check response data like value of adid is not empty & success is true
verify when new ad is created in newSysterm its replicated in legacySystem 
verify no Dulplicates when new ad is created in new system and legacySystem
Verify ad is deleted in both systems when removed in newSysterm



Used Java (HttpClients) with Junit and Jackson API for Marshalling and UnMarshalling between JSON to JAVA 
