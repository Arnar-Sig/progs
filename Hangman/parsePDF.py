import PyPDF2  
pdfFileObj = open('vocab.pdf', 'rb')
pdfReader = PyPDF2.PdfFileReader(pdfFileObj)
pageObj = []
for i in range(56):
    pageObj.append(pdfReader.getPage(i))

print(pageObj[5].extractText())
