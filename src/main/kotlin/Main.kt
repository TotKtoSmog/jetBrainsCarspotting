package parking
import kotlin.collections.ArrayList

class Car (val name: String, val color: String)
class Parking (){
    private var countSpot = 0
    private var cars: Array<Car?> = Array(countSpot){null}
    fun create(countSpot:Int){
        this.countSpot = countSpot
        cars = Array(countSpot){null}
        println("Created a parking lot with ${this.countSpot} spots.")
    }
    fun leave(numberLot:Int){
        if(!getStatus()) {
            if(carInSpot(numberLot)) {
                cars[getIndexSpot(numberLot)] = null
                println("Spot $numberLot is free.")
            } else {
                println("There is no car in spot $numberLot.")
            }
        }
    }
    fun park(car: Car){
        if(!getStatus()){
            val freeLotIndex = foundFreeSpotIndex()
            if(freeLotIndex == -1) {
                println("Sorry, the parking lot is full.")
            } else {
                cars[freeLotIndex] = car
                println("${car.color} car parked in spot ${freeLotIndex + 1}.")
            }
        }
    }
    fun showStatus(){
        if(!getStatus()) {
            if(cars.count { it != null } == 0) println("Parking lot is empty.") else showCars()
        }
    }

    private fun getRegByColor(color: String): ArrayList<String> {
        val tempCars = cars.filter { it?.color?.lowercase() == color.lowercase() }
        val result = arrayListOf<String>()
        for(item in tempCars)
            result.add(item?.name.toString())
        return result
    }
    fun showRegByColor(color: String){
        val carName = getRegByColor(color)
        if(!getStatus())
            println(if(carName.isNotEmpty()) carName.joinToString() else "No cars with color $color were found." )

    }
    private fun getSpotByColor(color: String): Array<Int?> {
        val array = arrayListOf<Int>()
        for (i in 0 until countSpot) if (cars[i]?.color?.lowercase() == color.lowercase()) array.add(
            i + 1
        )
        return array.toTypedArray()
    }
    fun showSpotByColor(color: String) {
        if(!getStatus()){
            val cars = getSpotByColor(color)
            println(if(cars.isNotEmpty()) cars.joinToString() else "No cars with color $color were found.")
        }
    }
    private fun getSpotByReg(name: String): Array<Int?>{
        val array = arrayListOf<Int>()
        for (i in 0 until countSpot) if(cars[i]?.name?.lowercase() == name.lowercase()) array.add(i+1)
        return array.toTypedArray()
    }
    fun showSpotByReg(name: String) {
        if(!getStatus()){
            val cars = getSpotByReg(name)
            println(if(cars.isNotEmpty()) cars.joinToString() else "No cars with registration number $name were found.")
        }
    }

    private fun showCars(){
        for(i in cars.indices) {
            if(cars[i] == null) continue
            println("${i + 1} ${cars[i]!!.name} ${cars[i]!!.color}")
        }
    }
    private fun getStatus(): Boolean {
        return if (countSpot <= 0) {
            println("Sorry, a parking lot has not been created.")
            true
        }else {
            false
        }
    }
    private fun getIndexSpot(numberLot:Int) : Int {
        return if (numberLot - 1 < 0) {
            0
        } else if (numberLot - 1 > countSpot) {
            countSpot-1
        } else numberLot - 1
    }
    private fun carInSpot(numberLot:Int): Boolean = cars[getIndexSpot(numberLot)] != null
    private fun foundFreeSpotIndex():Int {
        for (i in cars.indices) {
            if(cars[i] == null) return i
        }
        return -1
    }
}
fun main() {
    val myParking = Parking()
    do {
        val data = readln().split(' ')
        when(data[0]){
            "park" -> myParking.park(car = Car(data[1], data[2]))
            "leave" -> myParking.leave(data[1].toInt())
            "status" -> myParking.showStatus()
            "create" -> myParking.create(data[1].toInt())
            "reg_by_color" -> myParking.showRegByColor(data[1])
            "spot_by_color" -> myParking.showSpotByColor(data[1])
            "spot_by_reg" -> myParking.showSpotByReg(data[1])
        }
    } while(data[0].lowercase() != "exit")
}