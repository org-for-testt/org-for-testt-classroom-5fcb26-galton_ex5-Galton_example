import subprocess

for i in range(1, 1001):
    # result = subprocess.run(["mvn", "-Dtest=MainTest", "test"], capture_output=True, text=True)
    result = subprocess.run(["mvn", "test"], capture_output=True, text=True)
    
    if result.returncode != 0:
        print(f"Test failed on attempt #{i}")
        with open("test_output.log", "w") as f:
            f.write(result.stdout + "\n" + result.stderr)
        exit(1)
    
    print(f"Test passed #{i}")

print("✅ Test passed 1000 times!")
