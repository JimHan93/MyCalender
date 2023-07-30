import javax.swing.*;
import java.awt.*;
import java.util.Calendar;

public class MyCalendar extends JFrame{
    private JLabel monthYearL;
    private JButton priorB;
    private JButton nextB;
    private Calendar calendar;
    private JPanel datesP;

    public MyCalendar(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 300);
        setLayout(new BorderLayout());

        calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2022);
        calendar.set(Calendar.MONTH, Calendar.NOVEMBER);
        calendar.set(Calendar.DAY_OF_MONTH, 1);

        JPanel monthP = new JPanel();
        monthYearL = new JLabel(getMonthYearText());
        monthP.add(monthYearL);
        add(monthP, BorderLayout.NORTH);

        datesP = new JPanel(new GridLayout(0, 7));
        updateCalendar();

        add(datesP, BorderLayout.CENTER);

        JPanel buttonsP = new JPanel();
        priorB = new JButton("Prior");
        nextB = new JButton("Next");
        buttonsP.add(priorB);
        buttonsP.add(nextB);
        add(buttonsP, BorderLayout.SOUTH);

        priorB.addActionListener(e -> changeMonth(-1));
        nextB.addActionListener(e -> changeMonth(1));
    }

    private void changeMonth(int offset){
        calendar.add(Calendar.MONTH, offset);
        monthYearL.setText(getMonthYearText());
        updateCalendar();
    }

    private void updateCalendar(){
        datesP.removeAll();

        String[] days = {"Su", "Mo", "Tu", "We", "Th", "Fr", "Sa"};
        for (String day : days) {
            datesP.add(new JLabel(day, SwingConstants.CENTER));
        }

        Calendar cal = (Calendar) calendar.clone();
        cal.set(Calendar.DAY_OF_MONTH, 1);
        int maxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        int firstDayOfWeek = cal.get(Calendar.DAY_OF_WEEK);

        cal.add(Calendar.MONTH, -1);
        int maxDayOfLastMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

        int blankDays = (firstDayOfWeek - 1 + 7) % 7;
        for (int i = 0; i < blankDays; i++){
            JLabel greys = new JLabel(Integer.toString(maxDayOfLastMonth - blankDays + i + 1), SwingConstants.CENTER);
            greys.setForeground(Color.GRAY);
            datesP.add(greys);
        }

        for (int day = 1; day <= maxDay; day++){
            datesP.add(new JLabel(Integer.toString(day), SwingConstants.CENTER));
        }

        cal.add(Calendar.MONTH, 2);
        cal.set(Calendar.DAY_OF_MONTH, 1);

        int totalDays = blankDays + maxDay;
        int blanksForNextMonth = 42 - totalDays;

        for (int i = 0; i < blanksForNextMonth; i++){
            JLabel greys = new JLabel(Integer.toString(i + 1), SwingConstants.CENTER);
            greys.setForeground(Color.GRAY);
            datesP.add(greys);
        }

        datesP.validate();
        datesP.repaint();
    }

    private String getMonthYearText(){
        return String.format("%tB %<tY", calendar.getTime());
    }

    public static void main(String[] args){
        SwingUtilities.invokeLater(() -> new MyCalendar().setVisible(true));
    }
}