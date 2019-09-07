from mtcnn.mtcnn import MTCNN
import cv2
import face_recognition
import os
import numpy as np
from functools import partial

np.load = partial(np.load, allow_pickle = True)

def get_id_and_encodings(im_path = './images/'):
	files = os.listdir(im_path)
	ids = {}
	encodings = []
	for i, f in enumerate(files):
		path = os.path.join(im_path, f)
		im = cv2.imread(path)
		ids[i] = f.split('.')[0].title()
		encodings.append(face_recognition.face_encodings(im[...,[2,1,0]])[0])
	return ids, encodings

ids , encodings = get_id_and_encodings()
detector = MTCNN()
class FaceRecog:
	def __init__(self):
		self.detector = MTCNN()
		self.ids, self.encodings = get_id_and_encodings()
	
	def recognition(self, frame):
		pred = self.detector.detect_faces(img=frame)
		boxes = []
		for person in pred:
			boxes.append(person['box'])
		encodes = []
		for box in boxes:
			encode = face_recognition.face_encodings(frame[...,[2,1,0]][max(0,box[1]-40):min(box[1]+box[3]+40,frame.shape[0]), max(box[0]-40, 0):min(box[0]+box[2]+40, frame.shape[1]),:])
			if encode:
				encodes.append(encode[0])
			else:
				encodes.append(np.zeros((128,)))
		labels = []
		for encode in encodes:
			label = face_recognition.compare_faces(face_encoding_to_check=encode, known_face_encodings=self.encodings)
			label = np.array(label, dtype = np.int32)
			if np.max(label) == 1:
				labels.append(ids[np.argmax(label)])
			else:
				labels.append('Unknown')
		return labels, boxes
		
		
cap = cv2.VideoCapture(0)
facerecog = FaceRecog()
while(cap.isOpened()):
	ret, frame = cap.read()
	if ret == True:
		labels, boxes = facerecog.recognition(frame)
		if len(labels) != 0:
			for i, box in enumerate(boxes):
				cv2.rectangle(frame, (box[0], box[1]), (box[0]+box[2], box[1]+box[3]), (0,125,255), 4)
				try:
					cv2.putText(frame, labels[i], (box[0], box[1]-5), cv2.FONT_HERSHEY_SIMPLEX, 0.8, (0, 125, 255), 2, cv2.LINE_AA)
				except:
					pass
		cv2.imshow('Out', frame)
		if cv2.waitKey(1) & 0xFF == ord('q'):
			break
	else:
		break	
cap.release()
cv2.destroyAllWindows()
