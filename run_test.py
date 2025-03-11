import subprocess

command= ["mvn", "-Dtest=MainTest", "test"]

p=subprocess.Popen(command,stdout=subprocess.PIPE, stderr=subprocess.STDOUT)

seen_Results = False
for line in iter(p.stdout.readline , b''):
    line = line.decode('utf-8')
    if line.startswith("Running estu.ceng.edu.MainTest"):
        seen_Results = True
    
    if seen_Results == True:
        print(line.strip())

        
    # break
