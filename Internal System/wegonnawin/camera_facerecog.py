import os
import cv2
from base_camera import BaseCamera


from mtcnn.mtcnn import MTCNN
import face_recognition
import os
import numpy as np

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

## overriding allow pickles
from functools import partial
np.load = partial(np.load, allow_pickle=True)
detector = MTCNN()


class Camera(BaseCamera):
	video_source = 0

	def __init__(self):
		super(Camera, self).__init__()

	@staticmethod
	def set_video_source(source):
		Camera.video_source = source

	@staticmethod
	def frames():
		camera = cv2.VideoCapture(Camera.video_source)
		if not camera.isOpened():
			raise RuntimeError('Could not start camera.')

		while True:
			# read current frame
			_, frame = camera.read()
			pred = detector.detect_faces(img=frame)
			boxes = []
			for person in pred:
				boxes.append(person['box'])
			im = frame[:]
			encodes = []
			for box in boxes:
				encode = face_recognition.face_encodings(frame[...,[2,1,0]][max(0,box[1]-40):min(box[1]+box[3]+40,frame.shape[0]), max(box[0]-40, 0):min(box[0]+box[2]+40, frame.shape[1]),:])
				if encode:
					encodes.append(encode[0])
				else:
					encodes.append(np.zeros((128,)))
			labels = []
			for encode in encodes:
				label = face_recognition.compare_faces(face_encoding_to_check=encode, known_face_encodings=encodings)
				label = np.array(label, dtype = np.int32)
				if np.max(label) == 1:
					labels.append(ids[np.argmax(label)])
				else:
					labels.append('Unknown')
			if len(labels) != 0:
				for i, box in enumerate(boxes):
					cv2.rectangle(im, (box[0], box[1]), (box[0]+box[2], box[1]+box[3]), (0,125,255), 4)
					try:
						cv2.putText(im, labels[i], (box[0], box[1]-5), cv2.FONT_HERSHEY_SIMPLEX, 0.8, (0, 125, 255), 2, cv2.LINE_AA)
					except:
						pass
			# encode as a jpeg image and return it
			yield cv2.imencode('.jpg', im)[1].tobytes()
