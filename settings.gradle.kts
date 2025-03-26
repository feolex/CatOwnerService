rootProject.name = "CatOwnerService"

include("HybernateMVC")
include("HybernateMVC:ServiceLayer")
findProject(":HybernateMVC:ServiceLayer")?.name = "ServiceLayer"
include("HybernateMVC:DaoLayer")
findProject(":HybernateMVC:DaoLayer")?.name = "DaoLayer"
include("HybernateMVC:ControllerLayer")
findProject(":HybernateMVC:ControllerLayer")?.name = "ControllerLayer"
include("HybernateMVC:Common")
findProject(":HybernateMVC:Common")?.name = "Common"

include("SpringMVC")
include("SpringMVC:ServiceLayer")
findProject(":SpringMVC:ServiceLayer")?.name = "ServiceLayer"
include("SpringMVC:DaoLayer")
findProject(":SpringMVC:DaoLayer")?.name = "DaoLayer"
include("SpringMVC:ControllerLayer")
findProject(":SpringMVC:ControllerLayer")?.name = "ControllerLayer"
include("SpringMVC:Common")
findProject(":SpringMVC:Common")?.name = "Common"
include("SpringMVC:SecurityLayer")
findProject(":SpringMVC:SecurityLayer")?.name = "SecurityLayer"

include("Microservices")
include("Microservices:CatMicroservice")
include("Microservices:DiscoveryServer")
include("Microservices:OwnerMicroservice")

project(":Microservices:DiscoveryServer").projectDir = file("Microservices/DiscoveryServer")
project(":Microservices:OwnerMicroservice").projectDir = file("Microservices/OwnerMicroservice")
project(":Microservices:CatMicroservice").projectDir = file("Microservices/CatMicroservice")
