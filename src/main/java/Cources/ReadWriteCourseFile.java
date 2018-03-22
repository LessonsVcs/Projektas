package Cources;

import Cources.Course;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;


public class ReadWriteCourseFile {
    private String file;
    private HashMap<Integer, Course> courses= new HashMap();
    public ReadWriteCourseFile(){
        this.file = "courses.csv";
    }
    public void readCoursefile(){
        try (
                BufferedReader br = new BufferedReader(new FileReader(this.file))
        ) {
            String line = br.readLine();
            while ((line= br.readLine()) != null){
                String[] lines= line.split(";");
                this.courses.put(Integer.parseInt(lines[2]),new Course(lines[0],lines[1],lines[2]));
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void setCourses(HashMap<Integer, Course> courses) {
        this.courses = courses;
    }

    public HashMap<Integer, Course> getCourses() {
        return courses;
    }

    public void writeCourseFile(){
        String lineToWrite;
        try (
                BufferedWriter bw = new BufferedWriter(new FileWriter(this.file))
        ) {
            /*String firstName, String lastName, String password, String username,
         String role, String email, Date dateOfBirth, Integer personalNumber,
         String address, StringArray courses){
    */
            System.out.println(this.courses.size());
            bw.write("name, description, ID \n");
            for (Integer i: courses.keySet()) {
                lineToWrite = this.courses.get(i).getName() + ";" + this.courses.get(i).getDescription()
                + ";" + this.courses.get(i).getCourseID();
                bw.write(lineToWrite + "\n");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
