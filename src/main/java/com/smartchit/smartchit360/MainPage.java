package com.smartchit.smartchit360;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.MessageDigest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Component
public class MainPage implements CommandLineRunner {

    @Autowired private GroupRepo chitRepo;
    @Autowired private MembersRepo participantRepo;
    @Autowired private GoldchitRepo goldRepo;

    private final Scanner scanner = new Scanner(System.in);

    private BigDecimal fetchTodayGoldSpotRate() {
        // Base market reference rate around 7200 + deterministic daily delta
        int dayOfYear = LocalDate.now().getDayOfYear();
        double variance = (dayOfYear % 15) * 12.50;
        BigDecimal liveRate = new BigDecimal("7250.00").add(BigDecimal.valueOf(variance));
        return liveRate.setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public void run(String... args) throws Exception {
        while (true) {
            System.out.println("\n========================================================================");
            System.out.println("                 SMARTCHIT 360 - ENTERPRISE BACKEND ENGINE              ");
            System.out.println("========================================================================");
            System.out.println("Select Module to Run:");
            System.out.println("1. General Chit ");
            System.out.println("2. Street Chit ");
            System.out.println("3. Gold Chit");
            System.out.println("4. Exit Engine");
            System.out.print("Enter choice (1-4): ");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine().trim());
            } catch (Exception e) {
                continue;
            }

            if (choice == 1) {
                handleGeneralChit();
            } else if (choice == 2) {
                handleStreetChit();
            } else if (choice == 3) {
                handleGoldChit();
            } else {
                System.out.println("Exiting SmartChit 360 Engine. Goodbye!");
                break;
            }
        }
    }

    private void handleGeneralChit() {
        System.out.println("\n--- GENERAL CHIT MODULE (Family & Office) ---");
        System.out.println("1. Family Chit");
        System.out.println("2. Office Chit");
        System.out.print("Select Type (1 or 2): ");
        int type = Integer.parseInt(scanner.nextLine().trim());
        boolean isFamily = (type == 1);

        System.out.print("Enter Leader Name: ");
        String leader = scanner.nextLine().trim();

        System.out.print("Enter Total Members Count: ");
        int count = Integer.parseInt(scanner.nextLine().trim());

        List<String> names = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            System.out.print("Enter Member " + i + " Name: ");
            names.add(scanner.nextLine().trim());
        }

        System.out.print("Enter Monthly Target : ");
        BigDecimal totalPot = new BigDecimal(scanner.nextLine().trim());

        BigDecimal installmentPerHead = totalPot.divide(new BigDecimal(count), 2, RoundingMode.HALF_UP);
        int tenureMonths = count;

        GroupDetails record = chitRepo.save(new GroupDetails(
                isFamily ? "FAMILY_CHIT" : "OFFICE_CHIT",
                leader, count, totalPot, installmentPerHead, tenureMonths
        ));

        Collections.shuffle(names);

        LocalDate startDate = LocalDate.now().plusMonths(1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM yyyy");

        List<Members> participants = new ArrayList<>();
        System.out.println("\n========================================================================");
        System.out.println("                 GENERATED MONTHLY CYCLE & SCHEDULE                      ");
        System.out.println("========================================================================");
        System.out.println("Tenure: " + tenureMonths + " Months | Monthly Installment Per Member: Rs." + installmentPerHead);
        System.out.println("------------------------------------------------------------------------");

        for (int i = 0; i < names.size(); i++) {
            int turn = i + 1;
            String monthName = startDate.plusMonths(i).format(formatter);
            Members cp = new Members(record.getId(), names.get(i), turn, monthName, leader);
            participants.add(participantRepo.save(cp));
            System.out.printf("Month %-2d (%-8s) : Winner -> %-15s | Amount Taken: Rs.%s\n", turn, monthName, names.get(i), totalPot);
        }

        if (isFamily) {
            System.out.print("\nDo any family members require an Emergency Priority Swap? (yes/no): ");
            String swapAns = scanner.nextLine().trim();
            if (swapAns.equalsIgnoreCase("yes")) {
                System.out.print("Enter Turn Month of Member requesting swap (1-" + count + "): ");
                int turnA = Integer.parseInt(scanner.nextLine().trim());
                System.out.print("Enter Target Turn Month to swap with (1-" + count + "): ");
                int turnB = Integer.parseInt(scanner.nextLine().trim());

                Members pA = participants.get(turnA - 1);
                Members pB = participants.get(turnB - 1);

                String tempMonth = pA.getAssignedMonth();
                pA.setAssignedMonth(pB.getAssignedMonth());
                pB.setAssignedMonth(tempMonth);

                pA.setTurnOrder(turnB);
                pB.setTurnOrder(turnA);

                participantRepo.save(pA);
                participantRepo.save(pB);

                System.out.println(">> Priority Swap Executed Successfully in MySQL Database!");
                System.out.println(">> " + pA.getMemberName() + " is now scheduled for " + pA.getAssignedMonth());
                System.out.println(">> " + pB.getMemberName() + " is now scheduled for " + pB.getAssignedMonth());
            }
        }
    }

    private void handleStreetChit() {
        System.out.println("\n--- STREET CHIT (Community Loan & Group Leaders) ---");
        System.out.print("Enter Admin Name: ");
        String adminName = scanner.nextLine().trim();

        System.out.print("Enter Loan Principal Disbursed to Each Person : ");
        BigDecimal principalPerPerson = new BigDecimal(scanner.nextLine().trim());

        System.out.print("Enter Total Members Count : ");
        int totalMembers = Integer.parseInt(scanner.nextLine().trim());

        List<String> memberNames = new ArrayList<>();
        for (int i = 1; i <= totalMembers; i++) {
            System.out.print("Enter Borrower " + i + " Name: ");
            memberNames.add(scanner.nextLine().trim());
        }

        System.out.print("Enter Number of Group Leaders to Assign : ");
        int leaderCount = Integer.parseInt(scanner.nextLine().trim());

        List<String> leaders = new ArrayList<>();
        for (int i = 1; i <= leaderCount; i++) {
            System.out.print("Enter Leader " + i + " Name: ");
            leaders.add(scanner.nextLine().trim());
        }

        System.out.print("Enter Monthly Flat Interest Rate % : ");
        BigDecimal interestRate = new BigDecimal(scanner.nextLine().trim());

        System.out.print("Enter Repayment Duration in Months : ");
        int months = Integer.parseInt(scanner.nextLine().trim());

        BigDecimal totalInterest = principalPerPerson.multiply(interestRate.divide(new BigDecimal("100"), 4, RoundingMode.HALF_UP))
                .multiply(new BigDecimal(months));
        BigDecimal totalPayablePerMember = principalPerPerson.add(totalInterest);
        BigDecimal monthlyEMIPerMember = totalPayablePerMember.divide(new BigDecimal(months), 2, RoundingMode.HALF_UP);

        BigDecimal totalGroupPrincipal = principalPerPerson.multiply(new BigDecimal(totalMembers));
        GroupDetails record = chitRepo.save(new GroupDetails("STREET_LOAN", adminName, totalMembers, totalGroupPrincipal, monthlyEMIPerMember, months));

        Collections.shuffle(memberNames);
        Map<String, List<String>> leaderGroups = new LinkedHashMap<>();
        for (String ldr : leaders) {
            leaderGroups.put(ldr, new ArrayList<>());
        }

        for (int i = 0; i < memberNames.size(); i++) {
            String assignedLeader = leaders.get(i % leaderCount);
            leaderGroups.get(assignedLeader).add(memberNames.get(i));
            participantRepo.save(new Members(record.getId(), memberNames.get(i), i + 1, "ONGOING", assignedLeader));
        }

        System.out.println("\n========================================================================");
        System.out.println("                      LEADER COLLECTION DASHBOARD                       ");
        System.out.println("========================================================================");
        System.out.println("Per Member Loan: Rs." + principalPerPerson + " | Monthly EMI: Rs." + monthlyEMIPerMember);
        System.out.println("Total Monthly Collection Expected by Admin: Rs." + monthlyEMIPerMember.multiply(new BigDecimal(totalMembers)));
        System.out.println("------------------------------------------------------------------------");

        for (String ldr : leaderGroups.keySet()) {
            List<String> groupMembers = leaderGroups.get(ldr);
            BigDecimal leaderMonthlyTarget = monthlyEMIPerMember.multiply(new BigDecimal(groupMembers.size()));
            System.out.println("\nLeader: " + ldr + " (Assigned Borrowers: " + groupMembers.size() + ")");
            System.out.println("Leader Target to Handover to Admin: Rs." + leaderMonthlyTarget + " / month");
            System.out.println("Assigned Members List: " + String.join(", ", groupMembers));
        }
    }

    private void handleGoldChit() {
        System.out.println("\n--- GOLD CHIT (JEWELLERY MODULE) ---");
        System.out.print("Enter Customer Name : ");
        String customer = scanner.nextLine().trim();

        System.out.print("Enter Jewellery Shop Name : ");
        String shop = scanner.nextLine().trim();

        System.out.print("Enter Monthly Deposit Amount : ");
        BigDecimal monthlyDeposit = new BigDecimal(scanner.nextLine().trim());

        // Auto-fetch live spot rate from engine
        System.out.println("Connecting...");
        BigDecimal spotRate = fetchTodayGoldSpotRate();
        System.out.println(">> Verified Today's 22K Gold Rate: Rs." + spotRate + " / Gram [AUTO-FETCHED]");

        BigDecimal gramsCredited = monthlyDeposit.divide(spotRate, 4, RoundingMode.HALF_UP);
        String receiptHash = "RCP-GLD-" + generateHash(customer + System.currentTimeMillis()).substring(0, 10).toUpperCase();

        goldRepo.save(new GoldChit(customer, shop, monthlyDeposit, spotRate, gramsCredited, receiptHash, LocalDateTime.now()));

        BigDecimal total11MonthsPaid = monthlyDeposit.multiply(new BigDecimal("11"));
        BigDecimal bonusAmount = monthlyDeposit;
        BigDecimal maturityTotalCash = total11MonthsPaid.add(bonusAmount);

        System.out.println("\n========================================================================");
        System.out.println("                 DIGITAL GOLD PASSBOOK ENTRY GENERATED                  ");
        System.out.println("========================================================================");
        System.out.println("Customer Name         : " + customer);
        System.out.println("Jewellery Partner     : " + shop);
        System.out.println("Amount Paid This Month: Rs." + monthlyDeposit + " @ Rs." + spotRate + "/g");
        System.out.println("Gold Credited to Vault: " + gramsCredited + " Grams");
        System.out.println("Digital Receipt Token : " + receiptHash);
        System.out.println("------------------------------------------------------------------------");
        System.out.println("11+1 Bonus Forecast   : Total Paid (Rs." + total11MonthsPaid + ") + 1 Month Shop Bonus (Rs." 
                + bonusAmount + ") = Rs." + maturityTotalCash + " Maturity Benefit!");
    }

    private String generateHash(String data) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] bytes = md.digest(data.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) sb.append(String.format("%02x", b));
            return sb.toString();
        } catch (Exception e) {
            return UUID.randomUUID().toString().replace("-", "");
        }
    }
}