package pkg_queue_fiks;
import java.util.Scanner;

public class Pkg_Queue_Fiks {
    
    static class Node {
        String itemName;
        int price;
        Node prev, next;

        Node(String name, int price) {
            this.itemName = name;
            this.price = price;
        }
    }

    static class PurchaseQueue {
        Node front, rear;

        PurchaseQueue() {
            front = rear = null;
        }

        void enqueue(Node newNode) {
            if (rear == null) {
                front = rear = newNode;
            } else {
                rear.prev = newNode;
                newNode.next = rear;
                rear = newNode;
            }
            System.out.println(newNode.itemName + " berhasil ditambahkan ke antrian");
        }

        Node dequeue(String itemName) {
            if (front == null) {
                System.out.println("Antrian kosong");
                return null;
            }

            Node current = front;
            while (current != null && !current.itemName.equalsIgnoreCase(itemName)) {
                current = current.prev;
            }

            if (current == null) {
                System.out.println("Barang '" + itemName + "' tidak ditemukan");
                return null;
            }

            // Case 1: Hanya satu node
            if (front == rear) {
                front = rear = null;
            } 
            // Case 2: Node di front
            else if (current == front) {
                front = front.prev;
                front.next = null;
            } 
            // Case 3: Node di rear
            else if (current == rear) {
                rear = rear.next;
                rear.prev = null;
            } 
            // Case 4: Node di tengah
            else {
                current.next.prev = current.prev;
                current.prev.next = current.next;
            }

            System.out.println("Barang '" + current.itemName + "' berhasil diproses");
            return current;
        }

        void displayQueue() {
            if (front == null) {
                System.out.println("Antrian kosong");
                return;
            }

            System.out.println("\nDaftar Antrian Pembelian:");
            System.out.println("-------------------------");
            int position = 1;
            Node current = rear;
            while (current != null) {
                System.out.println(position + ". " + current.itemName + " - Rp" + current.price);
                current = current.next;
                position++;
            }
            System.out.println("-------------------------");

            int total = 0;
            current = front;
            while (current != null) {
                total += current.price;
                current = current.prev;
            }
            System.out.println("Total Transaksi: Rp" + total + "\n");
        }
    }

    public static void main(String[] args) {
        PurchaseQueue queue = new PurchaseQueue();
        Scanner scanner = new Scanner(System.in);
        int choice;
        int totalIncome = 0;
        
        String[] availableItems = {"Sepatu", "Tas", "Sandal"};
        int[] itemPrices = {200000, 150000, 100000};

        do {
            System.out.println("\nSISTEM ANTRIAN PEMBELIAN BARANG");
            System.out.println("===============================");
            System.out.println("1. Tambah Pembelian");
            System.out.println("2. Proses Pembelian");
            System.out.println("3. Lihat Antrian");
            System.out.println("4. Keluar");
            System.out.print("Pilihan Anda: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Clear buffer

            switch(choice) {
                case 1:
                    System.out.println("\nDaftar Barang Tersedia:");
                    for (int i = 0; i < availableItems.length; i++) {
                        System.out.println((i+1) + ". " + availableItems[i] + " - Rp" + itemPrices[i]);
                    }
                    System.out.print("Pilih barang (1-" + availableItems.length + "): ");
                    int itemChoice = scanner.nextInt() - 1;
                    scanner.nextLine(); // Clear buffer
                    
                    if (itemChoice >= 0 && itemChoice < availableItems.length) {
                        Node newItem = new Node(availableItems[itemChoice], itemPrices[itemChoice]);
                        queue.enqueue(newItem);
                    } else {
                        System.out.println("Pilihan tidak valid!");
                    }
                    break;
                    
                case 2:
                    queue.displayQueue();
                    if (queue.front != null) {
                        System.out.print("Masukkan nama barang yang akan diproses: ");
                        String itemToProcess = scanner.nextLine();
                        Node processedItem = queue.dequeue(itemToProcess);
                        if (processedItem != null) {
                            totalIncome += processedItem.price;
                            System.out.println("Total pemasukan: Rp" + totalIncome);
                        }
                    }
                    break;
                    
                case 3:
                    queue.displayQueue();
                    break;
                    
                case 4:
                    System.out.println("\nTerima kasih telah menggunakan sistem ini");
                    System.out.println("Total pemasukan hari ini: Rp" + totalIncome);
                    break;
                    
                default:
                    System.out.println("Pilihan tidak valid!");
            }
        } while (choice != 4);
        
        scanner.close();
    }
}