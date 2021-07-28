#Team30_project1
import argparse
#define class
class Assembly():
	def __init__(self):
		self.instruction_final = "test" #add everything to this, print at end
		self.inst_type = ""          #instruction type for sorting
		self.op_decimal = 0     #decimal vers of opcode
		self.op_word = ""       #opcode word (ADD, B, SUB...)
		self.zero_counter = 0   # need for tracking NOP
		self.bin_instruction = "" #for storing inst
		self.argument1 = ""
		self.argument2 = ""
		self.argument3 = ""
		self.valid = True  #initialize T, set to F when proven F

	def set_type(self, op_decimal):
		if op_decimal == 1112:
			self.op_word = "ADD"
			self.argument1 = "R" + str(self.convert_to_dec(self.bin_instruction[27:])) + ", "
			self.argument2 = "R" + str(self.convert_to_dec(self.bin_instruction[22:27])) + ", "
			self.argument3 = "R" + str(self.convert_to_dec(self.bin_instruction[11:16]))
		elif op_decimal == 1160 or  op_decimal == 1161:
			self.op_word ="ADDI"
			self.argument1 = "R" + str(self.convert_to_dec(self.bin_instruction[27:])) + ", "
			self.argument2 = "R" + str(self.convert_to_dec(self.bin_instruction[22:27])) + ", "
			self.argument3 = "#" + str(self.convert_to_dec(self.bin_instruction[10:22]))
		elif op_decimal == 1624:
			self.op_word = "SUB"
			self.argument1 = "R" + str(self.convert_to_dec(self.bin_instruction[27:])) + ", "
			self.argument2 = "R" + str(self.convert_to_dec(self.bin_instruction[22:27])) + ", "
			self.argument3 = "R" + str(self.convert_to_dec(self.bin_instruction[11:16]))
		elif op_decimal == 1672 or op_decimal == 1673:
			self.op_word = "SUBI"
			self.argument1 = "R" + str(self.convert_to_dec(self.bin_instruction[27:])) + ", "
			self.argument2 = "R" + str(self.convert_to_dec(self.bin_instruction[22:27])) + ", "
			self.argument3 = "#" + str(self.convert_to_dec(self.bin_instruction[10:22]))
		elif op_decimal == 1691:
			self.op_word = "LSL"
			self.argument1 = "R" + str(self.convert_to_dec(self.bin_instruction[27:])) + ", "
			self.argument2 = "R" + str(self.convert_to_dec(self.bin_instruction[22:27])) + ", "
			self.argument3 = "#" + str(self.convert_to_dec(self.bin_instruction[16:22]))
		elif op_decimal == 1690:
			self.op_word = "LSR"
			self.argument1 = "R" + str(self.convert_to_dec(self.bin_instruction[27:])) + ", "
			self.argument2 = "R" + str(self.convert_to_dec(self.bin_instruction[22:27])) + ", "
			self.argument3 = "#" + str(self.convert_to_dec(self.bin_instruction[16:22]))
		elif op_decimal == 1104:
			self.op_word = "AND"
			self.argument1 = "R" + str(self.convert_to_dec(self.bin_instruction[27:])) + ", "
			self.argument2 = "R" + str(self.convert_to_dec(self.bin_instruction[22:27])) + ", "
			self.argument3 = "R" + str(self.convert_to_dec(self.bin_instruction[11:16]))
		elif op_decimal == 1360:
			self.op_word = "ORR"
			self.argument1 = "R" + str(self.convert_to_dec(self.bin_instruction[27:])) + ", "
			self.argument2 = "R" + str(self.convert_to_dec(self.bin_instruction[22:27])) + ", "
			self.argument3 = "R" + str(self.convert_to_dec(self.bin_instruction[11:16]))
		elif op_decimal == 1872:
			self.op_word = "EOR"
			self.argument1 = "R" + str(self.convert_to_dec(self.bin_instruction[27:])) + ", "
			self.argument2 = "R" + str(self.convert_to_dec(self.bin_instruction[22:27])) + ", "
			self.argument3 = "R" + str(self.convert_to_dec(self.bin_instruction[11:16]))
		elif op_decimal == 1986:
			self.op_word = "LDUR"
			self.argument1 = "R" + str(self.convert_to_dec(self.bin_instruction[27:])) + ", "
			self.argument2 = "[R" + str(self.convert_to_dec(self.bin_instruction[22:27])) + ", "
			self.argument3 = "#" + str(self.convert_to_dec(self.bin_instruction[10:20])) + "]"
		elif op_decimal == 1984:
			self.op_word = "STUR"
			self.argument1 = "R" + str(self.convert_to_dec(self.bin_instruction[27:])) + ", "
			self.argument2 = "[R" + str(self.convert_to_dec(self.bin_instruction[22:27])) + ", "
			self.argument3 = "#" + str(self.convert_to_dec(self.bin_instruction[10:20])) + "]"
		elif 1440 <= op_decimal <= 1447:
			self.op_word = "CBZ"
			self.argument1 = "R" + str(self.convert_to_dec(self.bin_instruction[27:])) + ", "
			self.argument2 = "#" + str(self.convert_to_dec_2compliment(self.bin_instruction[8:27]))
			self.argument3 = ""
		elif  1448 <= op_decimal <= 1455:
			self.op_word = "CBNZ"
			self.argument1 = "R" + str(self.convert_to_dec(self.bin_instruction[27:])) + ", "
			self.argument2 = "#" + str(self.convert_to_dec_2compliment(self.bin_instruction[8:27]))
			self.argument3 = ""
		elif 1648 <= op_decimal <= 1687:
			self.op_word = "MOVZ"
			self.argument1 = "R" + str(self.convert_to_dec(self.bin_instruction[27:])) + ", "
			self.argument2 = str(self.convert_to_dec(self.bin_instruction[11:27])) + ", "
			self.argument3 = "LSL " + self.figure_shift(self.bin_instruction[9:11])
		elif 1940 <= op_decimal <= 1943:
			self.op_word = "MOVK"
			self.argument1 = "R" + str(self.convert_to_dec(self.bin_instruction[27:])) + ", "
			self.argument2 = str(self.convert_to_dec(self.bin_instruction[11:27])) + ", "
			self.argument3 = "LSL " + self.figure_shift(self.bin_instruction[9:11])
		elif 160 <= op_decimal <= 191:
			self.op_word = "B"
			self.argument1 = "#" + str(self.convert_to_dec_2compliment(self.bin_instruction[6:]))
			self.argument2 = ""
		elif self.zero_counter == 11:    ##11 zeros in opcode → NOP
			self.op_word = "NOP"
			self.argument1 = ""
			self.argument2 = ""
			self.argument3 = ""
		elif op_decimal == 2038:
			self.op_word = "BREAK"
			self.argument1 = ""
			self.argument2 = ""
			self.argument3 = ""
		else:
			self.valid = False

	def convert_to_dec(self, arg):
		decimal = 0
		arg = list(reversed(arg))
		for index in range(len(arg)):
			if arg[index] == "1":  # if bit is 0, don’t add
				current = 2 ** index
				decimal += current
		return decimal

	def convert_to_dec_2compliment(self, arg):
		decimal = 0
		if arg[0] != "1":
			arg = list(reversed(arg))
			for index in range(len(arg)):
				if arg[index] == "1":  # if bit is 0, don’t add
					current = 2 ** index
					decimal += current

		else:
			arg = list(reversed(arg))
			for index in range(len(arg)):
				if arg[index] == "0":  # if bit is 0, don’t add
					arg[index] = "1"
				else:
					arg[index] = "0"

			for index in range(len(arg)):
				if arg[index] == "1":  # if bit is 0, don’t add
					current = 2 ** index
					decimal += current

			decimal = (decimal + 1) * -1
		return decimal

	def figure_shift(self, shamt):
		if shamt == "00":
			return "0"
		elif shamt == "01":
			return "16"
		elif shamt == "10":
			return "32"
		elif shamt == "11":
			return "48"
		else:
			self.valid = False
			return "0"

#parse comand line arguments
def parse_args():
	parser = argparse.ArgumentParser(description='ARM(Stumpyleg)v8 dissasembler')
	parser.add_argument(
		'-i', #test1_bin.txt
		help='input bin text file')
	parser.add_argument(
		'-o', #team30_out
		#type=string,
		help='input bin text file')
	return parser.parse_args()

def run(in_file, out_file):
	#We need all these lists to store the data (array style). We'll need it later.
	op_code = []
	Arg1 = []
	Arg2 = []
	Arg3 = []
	Arg4 = []
	Arg5 = []
	PC = []
	instr_type_arg = []
	Reg_field1 = []
	Reg_field2 = []
	Reg_field3 = []


	 #= open("test1_bin.txt", "r")   #open file for reading
	obj_out = open(out_file + "_dis.txt", "w") ## open/create output file for writing
	line_count = 0
	invalid_counter = 0 #counts invalid instructions.  Will print -1, -2 etc if invalid!
	with open(in_file, "r") as read_obj:
		for line in read_obj:
			temp = line.rstrip('\n')
			code_obj = Assembly()
			#Everything should be in this block for it to read every line before closing
			op_code.append(temp[0:8])
			Arg1.append(temp[8:11])
			Arg2.append(temp[11:16])
			Arg3.append(temp[16:21])
			Arg4.append(temp[21:26])
			Arg5.append(temp[26:32])
			PC.append(96 + (line_count*4))    #this will increment the PC 4 each line and append

			#this line will collect everything for printing line vvv
			code_obj.instruction_final = temp[0:8] + " " + temp[8:11] + " " +  temp[11:16] + " " +temp[16:21] + " " + temp[21:26] + " " + temp[26:32] + "\t"
			code_obj.instruction_final += str(PC[line_count])
			code_obj.bin_instruction = line.rstrip()
			test = list(reversed(temp[0:11])) # start at  index 10, go back towards 0
			##This will determine decimal value of opcode vvv
			for index in range(len(test)):
				if test[index] == "0":  # if bit is 0, don’t add
					code_obj.zero_counter += 1  ##counter to check for NOP
				else:
					current = 2 ** index
					code_obj.op_decimal += current
			code_obj.set_type(code_obj.op_decimal)     #run set type on opcode decimal
			if code_obj.valid:       #if valid, operate as normal
				#Append ADD SUB B etc. to type column
				instr_type_arg.append(code_obj.op_word)
				#store arguments to keep data
				Reg_field1.append(code_obj.argument1)
				Reg_field2.append(code_obj.argument2)
				Reg_field3.append(code_obj.argument3)
				code_obj.instruction_final += "\t" + code_obj.op_word + "\t" + code_obj.argument1 + code_obj.argument2 + code_obj.argument3
			else:
				#somehow getting in here each pass, so valid is getting set to false in set_type
				invalid_counter -= 1
				code_obj.instruction_final = temp + "\t" + str(PC[line_count]) + "\t" + str(invalid_counter)
			code_obj.instruction_final += "\n"  ##append newline char for output, ready for next line
			obj_out.write(code_obj.instruction_final)   ##Writes full line to file
			line_count += 1
	##Once all lines have been written
	obj_out.close()	    #close read file
	read_obj.close()      # close write file
###################MAIN####################################################################
def main():
	arguments = parse_args()
	run(arguments.i, arguments.o)

if __name__== "__main__":
  main()
