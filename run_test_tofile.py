import subprocess

# Define the command to run Maven test
command = ["mvn", "-Dtest=MainTest", "test"]

# Open a file to store output
with open("test_output.txt", "w", encoding="utf-8") as output_file:
    p = subprocess.Popen(command, stdout=subprocess.PIPE, stderr=subprocess.STDOUT, text=True)

    seen_Results = False
    for line in iter(p.stdout.readline, ''):
        if line.startswith("Running estu.ceng.edu.MainTest"):
            seen_Results = True

        if seen_Results:
            output_file.write(line)  # Write to file
            output_file.flush()  # Ensure immediate writing to file
