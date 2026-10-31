import java.util.*;

public class Elevator {
	
	 Scanner scanner = new Scanner(System.in);
	// กำหนดให้ชั้นบนสุดคือชั้น 12
	 final int top_floor = 12;
	// กำหนดให้ชั้นล่างสุดคือชั้น 1
	 final int ground_floor = 1;
	 int current_floor = 1;
	// สร้าง ArrayList เก็บค่าชั้นที่ต้องไป
	 List<Integer> required_floor = new ArrayList<Integer>();

	// สร้าง Object และ run การทำงาน
    public static void main(String[] args) {
    	
        Elevator elevator = new Elevator();
        
        elevator.run();
        
    } // จบ method main()

	// เวลาการแสดงผลของ loop แต่ละครั้ง
    public void pause(int ms) { // ms คือ หน่วย milliseconds
    	
        try {
        	// ทำให้ลำดับการเรียงชั้นเหมือนการนับ
            Thread.sleep(ms);
            
        } catch (Exception ignored) {
        }
        
    } // จบ method pause()

	// ลิฟต์ขึ้น
    public void goUp(int closest_floor) {
    	
        int starting_floor = current_floor;
        
        System.out.println("Elevator is going up from floor " + starting_floor + " to floor: " + closest_floor);
        System.out.print(starting_floor + " ");
        
        // เรียงลำดับชั้นที่ผ่านก่อนถึงชั้นที่เลือก
        while (current_floor < closest_floor) {
            current_floor++;
            System.out.print(current_floor + " ");
            pause(1000); // 1 วิ ต่อการ loop 1 ครั้ง
        }
        
        System.out.println();
        
    } // จบ method goUp()

	// ลิฟต์ลง
    public void goDown(int closest_floor) {
    	
        int starting_floor = current_floor;
        
        System.out.println("Elevator is going down from floor " + starting_floor + " to floor: " + closest_floor);
        System.out.print(starting_floor + " ");
        
        // เรียงลำดับชั้นที่ผ่านก่อนถึงชั้นที่เลือก
        while (current_floor > closest_floor) {
            current_floor--;
            System.out.print(current_floor + " ");
            pause(1000); // 1 วิ ต่อการ loop 1 ครั้ง
            
        }
        
        System.out.println();
        
    } // จบ method goDown()

    // หาชั้นที่ใกล้ current_floor (ชั้นปัจจุบัน) ที่สุด
    public int closest_floor() {
    	
        int closest_floor = 0;
        int compare = Integer.MAX_VALUE;
        
        for (int i = 0; i < required_floor.size(); i++) {
        	
            if (Math.abs(current_floor - required_floor.get(i)) < compare) {
            	
                compare = Math.abs(current_floor - required_floor.get(i));
                closest_floor = required_floor.get(i);
                
            }
            
        }
        
        return closest_floor;
        
    } // // จบ method closest_floor()

    // รับข้อมูล เช็กความถูกต้องของข้อมูล และการเปิด/ปิดประตูลิฟต์
    public void run() {
    	
        int counter = 0;
        int upcoming_floor;

        System.out.println("The Current Floor is: " + current_floor);
        System.out.print("Please enter destination floor [1-12]: ");
        
        try {
            while (true) {
            	
                int next_floor = scanner.nextInt();
                
                System.out.println("The selected floor is: " + next_floor);
                System.out.println();
                
                // ใส่ 0 จบ session
                if (next_floor == 0) {
                	
                    break;
                    
                }
                // ใส่เลขที่ไม่ใช่ 1-12 หรือเลขเดียวกันกับ current_floor
                if (next_floor < ground_floor || next_floor > top_floor || next_floor == current_floor) {
                	
                    System.out.print("You have selected floor that doesnt exist or you are already on it, please select different floor.");
                    
                } 
                
                // ใส่เลขซ้ำ ลบเลขซ้ำออกตัวนึง
                else if (required_floor.contains(next_floor)) {
                	
                	required_floor.remove(Integer.valueOf(next_floor));
                	System.out.print("Please enter destination floor [1-12], or [0] to end current sequence of floors: ");
                    required_floor.add(next_floor);
                    counter++;
                	
                }
                
                // เคสปกติ
                else {
                	
                    System.out.print("Please enter destination floor [1-12], or [0] to end current sequence of floors: ");
                    required_floor.add(next_floor);
                    counter++;
                    
                }
                
                // ถ้าทั้ง 11 ชั้นถูกเลือก เริ่ม run
                if (counter == 11) {
                	
                    break;
                    
                }
                
            } // จบ loop while
            
        } // จบ try
       
        // ข้อมูลไม่ใช่ integer
        catch (Exception e) {
        	
            System.out.println("Please enter something from range of integers [1-12], or [0] to end current sequence of floors.");
            System.exit(0);
            
        } // จบ catch

        // Loop การทำงานของการเปิดและปิดประตูลิฟต์ตามจำนวนชั้นที่ถูกกด
        for (int i = 0; i < counter; i++) {

			// ArrayList ว่าง
            if (required_floor.isEmpty()){
            	
                System.out.println("Doors are closing....");
                System.out.println();    
                break;
                
            }
            
            System.out.println("The Current Floor is: " + current_floor);
            System.out.println("Selected floors are: " + required_floor);
            System.out.println("Doors are closing....");
            pause(1000); // 1 วิ

			// ให้ [ชั้นที่กำลังไป] เท่ากับ [ชั้นที่ใกล้กับ current_floor ที่สุด]
            upcoming_floor = closest_floor();
            
            // ถ้า upcoming_floor (ชั้นที่กำลังไป) น้อยกว่าชั้นปัจจุบันให้เรียกใช้ method goDown()
            if (upcoming_floor < current_floor) {

				// ให้ลิฟต์ลง
                goDown(upcoming_floor);
                
            } 
            
            // ถ้า upcoming_floor (ชั้นที่กำลังไป) มากกว่าชั้นปัจจุบันให้เรียกใช้ method goUp()
            else if (upcoming_floor > current_floor) {

				// ให้ลิฟต์ขึ้น
                goUp(upcoming_floor);
                
            }

            // ถ้าชั้นถัดไปเท่ากับชั้นปัจจุบัน ให้ลบชั้นนี้ออกจากลำดับ แล้วเปิดประตูลิฟต์
            required_floor.removeAll(Arrays.asList(upcoming_floor));
            System.out.println("Doors are opening...");
            System.out.println();            
            
        } // จบ loop for
        
        run();
        
    } // จบ method run()
    
} // จบ class Elevator
