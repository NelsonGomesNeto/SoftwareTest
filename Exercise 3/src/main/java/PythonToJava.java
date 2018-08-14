public class PythonToJava {

    boolean isPrime(int number) {
        if (number <= 1)
            return(false);
        for (int check = 2; check <= Math.sqrt(number); check++)
            if (number % check == 0)
                return(false);
        return(true);
    }
}

/*
Original code:
import math
def is_prime(number):
    if number <= 1 or (numbe % 2) == 0:
        return False
    for check in range(3, int(math.sqrt(number)):
        if number % check == 0:
            return False
    return True
*/