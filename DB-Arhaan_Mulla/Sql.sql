
CREATE DATABASE IF NOT EXISTS expense_tracker_db;
USE expense_tracker_db;


CREATE TABLE IF NOT EXISTS transactions (
    id INT AUTO_INCREMENT PRIMARY KEY,
    description VARCHAR(100) NOT NULL,
    category VARCHAR(50) NOT NULL,
    amount DECIMAL(10,2) NOT NULL,
    date DATE NOT NULL,
    type ENUM('income', 'expense') NOT NULL
);


INSERT INTO transactions (description, category, amount, date, type) VALUES
('Monthly Salary', 'Salary', 50000.00, '2025-10-01', 'income'),
('Freelance Project', 'Freelance', 15000.00, '2025-10-05', 'income'),
('Interest Earned', 'Bank', 1200.00, '2025-10-10', 'income'),
('Stock Dividend', 'Investments', 2500.00, '2025-10-15', 'income'),
('Part-time Job', 'Work', 8000.00, '2025-10-20', 'income'),

('Groceries', 'Food', 3200.00, '2025-10-03', 'expense'),
('Electricity Bill', 'Utilities', 1800.00, '2025-10-08', 'expense'),
('Movie Tickets', 'Entertainment', 900.00, '2025-10-11', 'expense'),
('Petrol', 'Transport', 2500.00, '2025-10-14', 'expense'),
('Online Shopping', 'Shopping', 4200.00, '2025-10-18', 'expense');

SELECT * FROM transactions ORDER BY date;


UPDATE transactions
SET amount = 4000.00, category = 'Online Purchase'
WHERE id = 10;


DELETE FROM transactions
WHERE id = 3;


SELECT SUM(amount) AS total_income
FROM transactions
WHERE type = 'income';


SELECT SUM(amount) AS total_expense
FROM transactions
WHERE type = 'expense';


SELECT 
    SUM(CASE WHEN type = 'income' THEN amount ELSE 0 END) AS total_income,
    SUM(CASE WHEN type = 'expense' THEN amount ELSE 0 END) AS total_expense,
    SUM(CASE WHEN type = 'income' THEN amount ELSE -amount END) AS net_balance
FROM transactions;
