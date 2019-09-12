compile:
	javac *.java

run:
	java $(MAIN)

clean:
	rm *.class

handin:	
	@mkdir src
	@cp *.java src/
	@cp README.pdf src/
	@zip -r $(ENTRY)_assignment$(ASSIGN_NO).zip src/
	@base64 $(ENTRY)_assignment$(ASSIGN_NO).zip > $(ENTRY)_assignment$(ASSIGN_NO).zip.b64
	#@rm $(ENTRY)_assignment2.zip
