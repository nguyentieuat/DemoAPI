-- public.bank definition

-- Drop table

-- DROP TABLE public.bank;

CREATE TABLE public.bank (
	id int8 NOT NULL,
	bank_code varchar(255) NULL,
	bank_name varchar(255) NULL,
	created_at timestamp NULL,
	updated_at timestamp NULL,
	CONSTRAINT bank_pkey PRIMARY KEY (id),
	CONSTRAINT uk_r03df0r93i07xw7u6cm066ub7 UNIQUE (bank_code)
);


-- public.blacklist definition

-- Drop table

-- DROP TABLE public.blacklist;

CREATE TABLE public.blacklist (
	id int8 NOT NULL,
	created_at timestamp NULL,
	description varchar(255) NULL,
	device_code varchar(255) NULL,
	"type" int4 NOT NULL,
	updated_at timestamp NULL,
	user_bank_code varchar(255) NULL,
	CONSTRAINT blacklist_pkey PRIMARY KEY (id)
);


-- public.business definition

-- Drop table

-- DROP TABLE public.business;

CREATE TABLE public.business (
	id int8 NOT NULL,
	business_code varchar(255) NULL,
	business_name varchar(255) NULL,
	created_at timestamp NULL,
	status int4 NOT NULL,
	updated_at timestamp NULL,
	CONSTRAINT business_pkey PRIMARY KEY (id),
	CONSTRAINT uk_o8i6f9007yxwooham363oc9bv UNIQUE (business_code)
);


-- public.users definition

-- Drop table

-- DROP TABLE public.users;

CREATE TABLE public.users (
	id int8 NOT NULL,
	created_at timestamp NULL,
	email varchar(255) NULL,
	"name" varchar(255) NULL,
	"password" varchar(255) NULL,
	phone varchar(255) NULL,
	"rank" int4 NOT NULL,
	status int4 NOT NULL,
	updated_at timestamp NULL,
	username varchar(255) NULL,
	CONSTRAINT uk_6dotkott2kjsp8vw4d0m25fb7 UNIQUE (email),
	CONSTRAINT uk_du5v5sr43g5bfnji4vb8hg5s3 UNIQUE (phone),
	CONSTRAINT uk_r43af9ap4edm43mmtq01oddj6 UNIQUE (username),
	CONSTRAINT users_pkey PRIMARY KEY (id)
);


-- public."transaction" definition

-- Drop table

-- DROP TABLE public."transaction";

CREATE TABLE public."transaction" (
	id int8 NOT NULL,
	amount float4 NOT NULL,
	created_at timestamp NULL,
	device_code varchar(255) NULL,
	transaction_id varchar(255) NULL,
	updated_at timestamp NULL,
	user_bank_code varchar(255) NULL,
	business_code varchar(255) NULL,
	username varchar(255) NULL,
	CONSTRAINT transaction_pkey PRIMARY KEY (id),
	CONSTRAINT uk_nevcwmpu8hb3a1naph2fyvqyu UNIQUE (transaction_id),
	CONSTRAINT fkhkn3jqv9sbc56yqk2s579fr6x FOREIGN KEY (business_code) REFERENCES public.business(business_code),
	CONSTRAINT fku4xb87ueyvyco52w24xs4676 FOREIGN KEY (username) REFERENCES public.users(username)
);


-- public.user_bank_business definition

-- Drop table

-- DROP TABLE public.user_bank_business;

CREATE TABLE public.user_bank_business (
	id int8 NOT NULL,
	created_at timestamp NULL,
	updated_at timestamp NULL,
	user_bank_code varchar(255) NULL,
	bank_code varchar(255) NULL,
	business_code varchar(255) NULL,
	username varchar(255) NULL,
	CONSTRAINT user_bank_business_pkey PRIMARY KEY (id),
	CONSTRAINT fkgkp6gux8brpwuk5stojgxetph FOREIGN KEY (username) REFERENCES public.users(username),
	CONSTRAINT fkrckvaf3pfjt09q6oti718fxp4 FOREIGN KEY (business_code) REFERENCES public.business(business_code),
	CONSTRAINT fkta17ve12xn132kjghsem7grjm FOREIGN KEY (bank_code) REFERENCES public.bank(bank_code)
);


-- public.user_device_business definition

-- Drop table

-- DROP TABLE public.user_device_business;

CREATE TABLE public.user_device_business (
	id int8 NOT NULL,
	created_at timestamp NULL,
	device_code varchar(255) NULL,
	device_name varchar(255) NULL,
	updated_at timestamp NULL,
	business_code varchar(255) NULL,
	username varchar(255) NULL,
	CONSTRAINT user_device_business_pkey PRIMARY KEY (id),
	CONSTRAINT fk7tdwphrc5seo52grquhgmglth FOREIGN KEY (username) REFERENCES public.users(username),
	CONSTRAINT fkcg4239u2uskyiqyoho58ukgxs FOREIGN KEY (business_code) REFERENCES public.business(business_code)
);


-- Insert data
INSERT INTO public.bank (id,bank_code,bank_name,created_at,updated_at) VALUES
	 (0,'VCB','VCB',NULL,NULL);
	 
INSERT INTO public.business (id,business_code,business_name,created_at,status,updated_at) VALUES
	 (0,'B_01',NULL,NULL,0,NULL),
	 (1,'B_02',NULL,NULL,0,NULL),
	 (2,'D_01',NULL,NULL,0,NULL),
	 (3,'F_04',NULL,NULL,0,NULL);
INSERT INTO public.user_bank_business (id,created_at,updated_at,user_bank_code,bank_code,business_code,username) VALUES
	 (0,NULL,NULL,'044106505656562','VCB','B_01','kk_009'),
	 (1,NULL,NULL,'044106505656562','VCB','B_02','zzzzzz'),
	 (2,NULL,NULL,'044106505656562','VCB','D_01','kk_009'),
	 (3,NULL,NULL,'044106505656562','VCB','F_04','kk_001');
INSERT INTO public.user_device_business (id,created_at,device_code,device_name,updated_at,business_code,username) VALUES
	 (0,NULL,'056f3a6a4828ff743da425215a637ba',NULL,NULL,'B_01','kk_009'),
	 (1,NULL,'056f3a6a4828ff743da425215a637ba',NULL,NULL,'B_02','zzzzzz'),
	 (2,NULL,'056f3a6a4828ff743da425215a637ba',NULL,NULL,'D_01','kk_009'),
	 (3,NULL,'056f3a6a4828ff743da425215a637ba',NULL,NULL,'F_04','kk_001');
INSERT INTO public.users (id,created_at,email,"name","password",phone,"rank",status,updated_at,username) VALUES
	 (0,NULL,NULL,NULL,NULL,NULL,0,0,NULL,'kk_009'),
	 (1,NULL,NULL,NULL,NULL,NULL,0,0,NULL,'kk_001'),
	 (2,NULL,NULL,NULL,NULL,NULL,0,0,NULL,'zzzzzz');
