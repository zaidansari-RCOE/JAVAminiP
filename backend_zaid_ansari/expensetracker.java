import java.io.*;
import java.util.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * Simple Expense Tracker backend (local file version)
 * Handles Add/Edit/Delete/View operations for transactions
 * Saves all data to "transactions.json" in the project folder
 */
public class ExpenseTracker {

    private static final String FILE_NAME = "transactions.json";
    private static final Gson gson = new Gson();

    // Transaction model
    static class Transaction {
        int id;
        String description;
        String category;
        double amount;
        String date;
        String type; // "income" or "expense"

        Transaction(int id, String description, String category, double amount, String date, String type) {
            this.id = id;
            this.description = description;
            this.category = category;
            this.amount = amount;
            this.date = date;
            this.type = type;
        }
    }

    private List<Transaction> transactions;

    public ExpenseTracker() {
        transactions = loadTransactions();
    }

    // ✅ Add new transaction
    public void addTransaction(String description, String category, double amount, String date, String type) {
        int newId = transactions.isEmpty() ? 1 : transactions.get(transactions.size() - 1).id + 1;
        Transaction t = new Transaction(newId, description, category, amount, date, type);
        transactions.add(t);
        saveTransactions();
        System.out.println("✅ Transaction added successfully!");
    }

    // ✅ Edit existing transaction by ID
    public void editTransaction(int id, String description, String category, double amount, String date, String type) {
        for (Transaction t : transactions) {
            if (t.id == id) {
                t.description = description;
                t.category = category;
                t.amount = amount;
                t.date = date;
                t.type = type;
                saveTransactions();
                System.out.println("✏️ Transaction updated successfully!");
                return;
            }
        }
        System.out.println("⚠️ Transaction ID not found!");
    }

    // ✅ Delete transaction
    public void deleteTransaction(int id) {
        boolean removed = transactions.removeIf(t -> t.id == id);
        if (removed) {
            saveTransactions();
            System.out.println("🗑️ Transaction deleted successfully!");
        } else {
            System.out.println("⚠️ Transaction ID not found!");
        }
    }

    // ✅ Show all transactions
    public void listTransactions() {
        if (transactions.isEmpty()) {
            System.out.println("No transactions found.");
            return;
        }

        System.out.println("-------------------------------------------------------------");
        System.out.printf("%-4s %-10s %-20s %-12s %-10s %-10s%n", "ID", "Type", "Description", "Category", "Amount", "Date");
        System.out.println("-------------------------------------------------------------");
        for (Transaction t : transactions) {
            System.out.printf("%-4d %-10s %-20s %-12s ₹%-9.2f %-10s%n",
                    t.id, t.type.toUpperCase(), t.description, t.category, t.amount, t.date);
        }
        System.out.println("-------------------------------------------------------------");

        calculateSummary();
    }

    // ✅ Summary (Total Income, Expense, and Balance)
    public void calculateSummary() {
        double income = 0, expense = 0;
        for (Transaction t : transactions) {
            if ("income".equalsIgnoreCase(t.type)) income += t.amount;
            else expense += t.amount;
        }
        double balance = income - expense;
        System.out.printf("Total Income: ₹%.2f | Total Expense: ₹%.2f | Net Balance: ₹%.2f%n",
                income, expense, balance);
    }

    // ✅ Save data to JSON file
    private void saveTransactions() {
        try (Writer writer = new FileWriter(FILE_NAME)) {
            gson.toJson(transactions, writer);
        } catch (IOException e) {
            System.err.println("Error saving transactions: " + e.getMessage());
        }
    }

    // ✅ Load data from JSON file
    private List<Transaction> loadTransactions() {
        try (Reader reader = new FileReader(FILE_NAME)) {
            return gson.fromJson(reader, new TypeToken<List<Transaction>>() {}.getType());
        } catch (FileNotFoundException e) {
            return new ArrayList<>();
        } catch (IOException e) {
            System.err.println("Error loading transactions: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    // 🧪 Quick CLI test (you can remove later)
    public static void main(String[] args) {
        ExpenseTracker tracker = new ExpenseTracker();
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- EXPENSE TRACKER ---");
            System.out.println("1. Add Transaction");
            System.out.println("2. Edit Transaction");
            System.out.println("3. Delete Transaction");
            System.out.println("4. View Transactions");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            int choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    System.out.print("Description: ");
                    String desc = sc.nextLine();
                    System.out.print("Category: ");
                    String cat = sc.nextLine();
                    System.out.print("Amount: ");
                    double amt = sc.nextDouble(); sc.nextLine();
                    System.out.print("Date (YYYY-MM-DD): ");
                    String date = sc.nextLine();
                    System.out.print("Type (income/expense): ");
                    String type = sc.nextLine();
                    tracker.addTransaction(desc, cat, amt, date, type);
                    break;
                case 2:
                    System.out.print("Enter ID to edit: ");
                    int idEdit = sc.nextInt(); sc.nextLine();
                    System.out.print("New Description: ");
                    desc = sc.nextLine();
                    System.out.print("New Category: ");
                    cat = sc.nextLine();
                    System.out.print("New Amount: ");
                    amt = sc.nextDouble(); sc.nextLine();
                    System.out.print("New Date (YYYY-MM-DD): ");
                    date = sc.nextLine();
                    System.out.print("New Type (income/expense): ");
                    type = sc.nextLine();
                    tracker.editTransaction(idEdit, desc, cat, amt, date, type);
                    break;
                case 3:
                    System.out.print("Enter ID to delete: ");
                    int idDel = sc.nextInt();
                    tracker.deleteTransaction(idDel);
                    break;
                case 4:
                    tracker.listTransactions();
                    break;
                case 5:
                    System.out.println("Goodbye 👋");
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}
