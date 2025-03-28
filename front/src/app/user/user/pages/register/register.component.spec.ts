import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { RegisterComponent } from './register.component';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { Router } from '@angular/router';

fdescribe('RegisterComponent', () => {
  let component: RegisterComponent;
  let fixture: ComponentFixture<RegisterComponent>;
  let httpMock: HttpTestingController;
  let routerSpy: jasmine.SpyObj<Router>;

  beforeEach(async () => {
    routerSpy = jasmine.createSpyObj('Router', ['navigate']);

    await TestBed.configureTestingModule({
      imports: [
        RegisterComponent,
        ReactiveFormsModule,
        HttpClientTestingModule
      ],
      providers: [
        { provide: Router, useValue: routerSpy }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(RegisterComponent);
    component = fixture.componentInstance;
    httpMock = TestBed.inject(HttpTestingController);
    fixture.detectChanges();
  });

  afterEach(() => {
    httpMock.verify(); 
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should navigate to home on cancel', () => {
    component.onCancel();
    expect(routerSpy.navigate).toHaveBeenCalledWith(['/']);
  });

  it('should initialize the form with empty fields', () => {
    const form = component.registerForm;
    expect(form).toBeDefined();
    expect(form.get('name')?.value).toBe('');
    expect(form.get('email')?.value).toBe('');
    expect(form.get('password')?.value).toBe('');
  });

  it('should not submit if form is invalid', () => {
    component.registerForm.patchValue({
      name: '',
      email: 'invalid-email',
      password: ''
    });
    component.onSubmit();
    httpMock.expectNone('/auth/user/register');
  });

  it('should submit and navigate on successful register', fakeAsync(() => {
    component.registerForm.setValue({
      name: 'Juan',
      email: 'juan@example.com',
      password: '123456',
      contactNumber: '123456789'
    });

    component.onSubmit();

    const req = httpMock.expectOne('/auth/user/register');
    req.flush({ token: 'abc123' });

    tick();
    expect(sessionStorage.getItem('token')).toBe('abc123');
    expect(routerSpy.navigate).toHaveBeenCalledWith(['/']);
  }));

  it('should show error message on failed register', fakeAsync(() => {
    component.registerForm.setValue({
      name: 'Ana',
      email: 'ana@example.com',
      password: '123456',
      contactNumber: ''
    });

    component.onSubmit();

    const req = httpMock.expectOne('/auth/user/register');
    req.flush({ error: 'Error' }, { status: 400, statusText: 'Bad Request' });

    tick();
    expect(component.errorMessage).toBe('Error al registrar. Intenta con otro email.');
  }));
});
