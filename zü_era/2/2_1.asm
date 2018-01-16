zeitdauer_tabelle:
	dw 10, 20, 30, 40

waschprogramm:
	resd 1

zeitdauer:
	resd 1

mov [waschprogramm], 50

xor eax, eax
mov ebx, zeitdauer_tabelle
add ax, [ebx]
add ax, [ebx+2]
add ax, [ebx+4]
add eax, [waschprogramm]
mov [zeitdauer], eax
